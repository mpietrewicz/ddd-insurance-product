package pl.mpietrewicz.insurance.product.domain.agregate.offer;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.NoArgsConstructor;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.ddd.support.infrastructure.repo.BaseEntity;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.dto.AcceptedProduct;
import pl.mpietrewicz.insurance.product.domain.service.promotion.policy.PromotionPolicy;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
public class Offering extends BaseEntity {

    @Embedded
    @AttributeOverride(name = "aggregateId", column = @Column(name = "productId", nullable = false))
    private ProductId productId;

    @Enumerated
    private List<PromotionType> usedPromotions;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "premium", nullable = false))
    private Premium premium;

    public Offering(ProductId productId, Premium premium) {
        this.productId = productId;
        this.premium = premium;
    }

    public boolean canAddPromotion(PromotionType promotionType) {
        return true; // todo: popraiwć
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

    public boolean apply(Long offeringId) {
        return Objects.equals(this.entityId, offeringId);
    }

    public boolean applyProduct(ProductId productId) {
        return this.productId.equals(productId);
    }

    public AcceptedProduct convertToAcceptedProduct() {
        return new AcceptedProduct(productId, Premium.TEN, usedPromotions);
    }

}
