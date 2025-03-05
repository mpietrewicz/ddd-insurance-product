package pl.mpietrewicz.insurance.product.domain.agregate.product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.NoArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.domain.AggregateRoot;
import pl.mpietrewicz.insurance.ddd.annotations.domain.InvariantsList;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.ddd.support.infrastructure.repo.BaseAggregateRoot;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.dto.AcceptedProduct;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.time.LocalDate;
import java.util.List;

import static pl.mpietrewicz.insurance.product.commonutil.DateComparison.isAfterOrEqual;
import static pl.mpietrewicz.insurance.product.commonutil.DateComparison.isBeforeOrEqual;

@InvariantsList({
        "eligibilities: product is eligibility to offer when all eligibilities are satisfied by insured"
})

@AggregateRoot
@Entity
@NoArgsConstructor
public class Product extends BaseAggregateRoot<ProductId> {

    private String name;

    @Embedded
    private Premium premium;

    private LocalDate validFrom;

    private LocalDate validTo;

    @Enumerated(EnumType.STRING)
    private PromotionType promotionType;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<Eligibility> eligibilityList;

    public Product(ProductId productId, BasicProductData basicProductData, List<Eligibility> eligibilityList) {
        this.aggregateId = productId;
        this.name = basicProductData.getName();
        this.premium = basicProductData.getPremium();
        this.validFrom = basicProductData.getValidFrom();
        this.validTo = basicProductData.getValidTo();
        this.promotionType = basicProductData.getPromotionType();
        this.eligibilityList = eligibilityList;
    }

    public ProductId getProductId() {
        return aggregateId;
    }

    public boolean matches(AcceptedProduct acceptedProduct) {
        return this.aggregateId == acceptedProduct.getProductId();
    }

    public boolean isEligible(ApplicantData applicantData) {
        return eligibilityList.stream()
                .allMatch(eligibility -> eligibility.isSatisfiedBy(applicantData));
    }

    public boolean isAvailableAt(LocalDate date) {
        return isAfterOrEqual(date, validFrom)
                && (validTo == null || isBeforeOrEqual(date, validTo));
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }

    public Premium getPremium() {
        return premium;
    }

}