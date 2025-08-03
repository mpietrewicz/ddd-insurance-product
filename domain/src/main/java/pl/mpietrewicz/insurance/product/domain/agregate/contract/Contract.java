package pl.mpietrewicz.insurance.product.domain.agregate.contract;

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
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.InsuredId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.support.infrastructure.repo.BaseAggregateRoot;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@InvariantsList({
        "close: only not closed contract can be closed"
})

@AggregateRoot
@Entity
@NoArgsConstructor
public class Contract extends BaseAggregateRoot<ContractId> {

    @Getter
    @Embedded
    @AttributeOverride(name = "aggregateId", column = @Column(name = "applicantId", nullable = false))
    private InsuredId insuredId;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "contract_id")
    private List<Component> components;

    private boolean closed;

    public Contract(ContractId contractId, InsuredId insuredId, List<Component> components) {
        this.aggregateId = contractId;
        this.components = components;
        this.insuredId = insuredId;
        this.closed = false;
    }

    public boolean canAddProduct(ProductId productId, LocalDate startDate) {
        return components.stream()
                .filter(component -> component.isSameProduct(productId))
                .noneMatch(component -> component.isActiveAt(startDate));
    }

    public boolean isActive() {
        return !closed;
    }

    public boolean canClose() {
        return isActive();
    }

    public void close() {
        this.closed = true;
    }

    public boolean canTerminate() {
        // todo: implement me!
        return true;
    }

    public void terminate() {
        // todo: implement me!
    }

    public List<UsedPromotion> getUsedPromotions(ProductId productId) {
        return components.stream()
                .filter(component -> component.isSameProduct(productId))
                .flatMap(component -> component.getUsedPromotion().stream())
                .toList();
    }

}
