package pl.mpietrewicz.insurance.product.domain.agregate.offer;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.NoArgsConstructor;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.dto.AcceptedProduct;
import pl.mpietrewicz.insurance.product.domain.service.promotion.policy.PromotionPolicy;
import pl.mpietrewicz.insurance.product.domainapi.dto.offering.OfferingId;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Offering {

    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private OfferingId id;

    @Embedded
    @AttributeOverride(name = "aggregateId", column = @Column(name = "productId", nullable = false))
    private ProductId productId;

    @ElementCollection
    @CollectionTable(name = "offering_used_promotion")
    @Enumerated(EnumType.STRING)
    private List<PromotionType> usedPromotions = new ArrayList<>();

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "premium", nullable = false))
    private Premium premium;

    public Offering(OfferingId id, ProductId productId, Premium premium) {
        this.id = id;
        this.productId = productId;
        this.premium = premium;
    }

    public ProductId getProductId() {
        return productId;
    }

    public boolean canApplyPromotion(PromotionType promotionType) {
        // todo: gdy już nie obniża składki to nie ma sensu dodawać tej promocji

        return !usedPromotions.contains(promotionType);
    }

    public void applyPromotion(PromotionPolicy policy, PromotionType promotionType) {
        if (this.usedPromotions.contains(promotionType)) {
            throw new IllegalStateException("Offering already contains promotion of type " + promotionType);  //
            // todo: dać inny wyjątek ?
        }

        Premium premiumDiscount = policy.calculateDiscount(this.premium);
        this.premium = this.premium.subtract(premiumDiscount);
        this.usedPromotions.add(promotionType);
    }

    public void revokePromotion(PromotionType promotionType) {
        // todo: remove discount
        usedPromotions.remove(promotionType);
    }

    public List<PromotionType> listRevocablePromotions() {
        return usedPromotions;
    }

    public boolean matches(OfferingId offeringId) {
        return this.id.equals(offeringId);
    }

    public boolean matches(ProductId productId) {
        return this.productId.equals(productId);
    }

    public AcceptedProduct convertToAcceptedProduct() {
        return new AcceptedProduct(productId, Premium.TEN, usedPromotions);
    }

}
