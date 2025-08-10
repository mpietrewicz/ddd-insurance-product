package pl.mpietrewicz.insurance.product.domain.agregate.offer;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.domain.AggregateRoot;
import pl.mpietrewicz.insurance.ddd.annotations.domain.InvariantsList;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.AccountingDate;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.ddd.support.infrastructure.repo.BaseAggregateRoot;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.dto.AcceptedOffer;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.dto.AcceptedProduct;
import pl.mpietrewicz.insurance.product.domain.service.offer.policy.OfferStartPolicy;
import pl.mpietrewicz.insurance.product.domain.service.promotion.policy.PromotionPolicy;
import pl.mpietrewicz.insurance.product.domainapi.dto.offering.OfferingKey;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;
import pl.mpietrewicz.insurance.product.domainapi.exception.CannotAcceptOfferException;
import pl.mpietrewicz.insurance.product.domainapi.exception.CannotChangeStartDateException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static jakarta.persistence.CascadeType.ALL;

@InvariantsList({
        "accepted: offer can be accepted only once",
        "products: offer must contain at least one product to be accepted",
        "acceptanceDate: acceptance date must be before offer start date",
        "startDate: start date can be changed when offer is not accepted and accounting date is before new start date",
        "startDate: start date can be changed when accounting date is before new start date"
})

@AggregateRoot
@Entity
@NoArgsConstructor
public class Offer extends BaseAggregateRoot<OfferId> {

    @Embedded
    @AttributeOverride(name = "aggregateId", column = @Column(name = "applicantId", nullable = false))
    @Getter
    private ApplicantId applicantId;

    @Getter
    private LocalDate startDate;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "offer_id")
    private List<Offering> offerings = new ArrayList<>();

    private boolean accepted = false;

    public Offer(OfferId offerId, ApplicantId applicantId, LocalDate startDate) {
        this.aggregateId = offerId;
        this.applicantId = applicantId;
        this.startDate = startDate;
    }

    public OfferId getOfferId() {
        return aggregateId;
    }

    public Long addOffering(ProductId productId, Premium premium) {
        Offering offering = new Offering(productId, premium);
        offerings.add(offering);
        return offering.getEntityId();
    }

    public void removeOffering(OfferingKey offeringKey) {
        Long offeringId = offeringKey.getOfferingId();
        offerings.removeIf(offering -> offering.matches(offeringId));
    }

    public ProductId getProductId(OfferingKey offeringKey) {
        return offerings.stream()
                .filter(offering -> offering.matches(offeringKey.getOfferingId()))
                .map(Offering::getProductId)
                .findAny()
                .orElseThrow(); // todo: zwórócić wyjatek że nie można znaleźć offeringu
    }

    public boolean canApplyPromotion(PromotionType promotionType, ProductId productId) {
        return getOffering(productId)
                .map(offering -> offering.canApplyPromotion(promotionType))
                .orElse(false);
    }

    public void applyPromotion(OfferingKey offeringKey, PromotionPolicy policy, PromotionType promotionType) {
        Optional<Offering> offering = offerings.stream()
                .filter(o -> o.matches(offeringKey.getOfferingId()))
                .findAny();
        if (offering.isPresent()) {
            offering.get().applyPromotion(policy, promotionType);
        } else {
            throw new NoSuchElementException("No offering for offering: " + offeringKey); // todo: dodać
            // customowy wyjatek ?
        }
    }

    public void revokePromotion(PromotionType promotionType, OfferingKey offeringKey) {
        offerings.stream()
                .filter(offering -> offering.matches(offeringKey.getOfferingId()))
                .findAny()
                .ifPresent(offering -> offering.revokePromotion(promotionType));
    }

    public List<PromotionType> listRevocablePromotions(OfferingKey offeringKey) {
        Long offeringId = offeringKey.getOfferingId();
        Offering offering = offerings.stream()
                .filter(o -> o.matches(offeringId))
                .findAny()
                .orElseThrow();
        return offering.listRevocablePromotions();
    }

    public List<LocalDate> getAvailableStartDates(OfferStartPolicy offerStartPolicy, AccountingDate accountingDate) {
        if (!accepted && accountingDate.isBefore(startDate)) {
            List<LocalDate> offerStartDates = offerStartPolicy.determine(accountingDate);
            offerStartDates.remove(startDate);
            return offerStartDates;
        } else {
            return Collections.emptyList();
        }
    }

    public void changeStartDate(LocalDate startDate) {
        if (!accepted) {
            this.startDate = startDate;
        } else {
            throw new CannotChangeStartDateException();
        }
    }

    public boolean canByAccepted(AccountingDate accountingDate) {
        return !accepted && !offerings.isEmpty() && accountingDate.isBefore(startDate);
    }

    public AcceptedOffer accept(AccountingDate accountingDate) {
        if (!canByAccepted(accountingDate)) {
            throw new CannotAcceptOfferException();
        }

        accepted = true;

        List<AcceptedProduct> acceptedProducts = getAcceptedProducts();
        return new AcceptedOffer(startDate, acceptedProducts);
    }

    public boolean contains(ProductId productId) {
        return offerings.stream()
                .anyMatch(offering -> offering.matches(productId));
    }

    public boolean contains(List<ProductId> productIds) {
        return offerings.stream()
                .allMatch(offering -> productIds.stream()
                        .anyMatch(offering::matches));
    }

    private Optional<Offering> getOffering(ProductId productId) {
        return offerings.stream()
                .filter(offering -> offering.matches(productId))
                .findAny();
    }

    private List<AcceptedProduct> getAcceptedProducts() {
        return getAcceptedOffering().stream()
                .map(Offering::convertToAcceptedProduct)
                .toList();
    }

    private List<Offering> getAcceptedOffering() {
        return offerings;
    }

}
