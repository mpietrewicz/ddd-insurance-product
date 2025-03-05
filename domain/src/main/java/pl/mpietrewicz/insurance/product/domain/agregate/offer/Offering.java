package pl.mpietrewicz.insurance.product.domain.agregate.offer;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.ddd.support.infrastructure.repo.BaseEntity;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.dto.AcceptedProduct;

import java.util.Objects;

@Entity
@NoArgsConstructor
public class Offering extends BaseEntity {

    @Embedded
    @AttributeOverride(name = "aggregateId", column = @Column(name = "productId", nullable = false))
    private ProductId productId;

    private boolean promotion;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "premium", nullable = false))
    private Premium premium;

    public Offering(ProductId productId, Premium premium, boolean promotion) {
        this.productId = productId;
        this.premium = premium;
        this.promotion = promotion;
    }

    public boolean apply(Long offeringId) {
        return Objects.equals(this.entityId, offeringId);
    }

    public boolean applyProduct(ProductId productId) {
        return this.productId.equals(productId);
    }

    public AcceptedProduct convertToAcceptedProduct() {
        return new AcceptedProduct(productId, promotion, Premium.TEN);
    }

}
