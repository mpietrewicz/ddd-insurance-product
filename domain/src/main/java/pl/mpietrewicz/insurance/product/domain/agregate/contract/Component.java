package pl.mpietrewicz.insurance.product.domain.agregate.contract;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.NoArgsConstructor;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.ddd.support.infrastructure.repo.BaseEntity;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.time.LocalDate;
import java.util.List;
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

    @Enumerated
    private List<PromotionType> usedPromotions;

    public Component(ProductId productId, LocalDate startDate, Premium premium, List<PromotionType> usedPromotions) {
        this.productId = productId;
        this.startDate = startDate;
        this.premium = premium;
        this.usedPromotions = usedPromotions;
    }

    public boolean isSameProduct(ProductId productId) {
        return this.productId == productId;
    }

    public boolean isActiveAt(LocalDate date) {
        return endDate == null || endDate.isBefore(date);
    }

    public Optional<UsedPromotion> getUsedPromotion() {
        return isPromotionUsed()
                ? Optional.of(new UsedPromotion(usedPromotions, startDate, endDate))
                : Optional.empty();
    }

    private boolean isPromotionUsed() {
        return !usedPromotions.isEmpty() && endDate.isAfter(startDate);
    }

}