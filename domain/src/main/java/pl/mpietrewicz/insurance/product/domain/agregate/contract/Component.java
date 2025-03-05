package pl.mpietrewicz.insurance.product.domain.agregate.contract;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.ddd.support.infrastructure.repo.BaseEntity;

import java.time.LocalDate;
import java.util.Optional;

@Entity
@NoArgsConstructor
public class Component extends BaseEntity {

    @Embedded
    @AttributeOverride(name = "aggregateId", column = @Column(name = "productId", nullable = false))
    private ProductId productId;

    private LocalDate startDate;

    private LocalDate endDate;

    @Embedded
    private Premium premium;

    private boolean promotional;

    public Component(ProductId productId, LocalDate startDate, Premium premium, boolean promotional) {
        this.productId = productId;
        this.startDate = startDate;
        this.premium = premium;
        this.promotional = promotional;
    }

    public boolean isSameProduct(ProductId productId) {
        return this.productId == productId;
    }

    public boolean isActiveAt(LocalDate date) {
        return endDate == null || endDate.isBefore(date);
    }

    public Optional<UsedPromotion> getUsedPromotion() {
        return isPromotionUsed()
                ? Optional.of(new UsedPromotion(startDate, endDate))
                : Optional.empty();
    }

    private boolean isPromotionUsed() {
        return promotional && endDate.isAfter(startDate);
    }

}