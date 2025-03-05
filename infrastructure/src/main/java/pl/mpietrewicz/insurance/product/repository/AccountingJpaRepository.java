package pl.mpietrewicz.insurance.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainRepositoryImpl;
import pl.mpietrewicz.insurance.product.domain.snapshot.Accounting;

@DomainRepositoryImpl
public interface AccountingJpaRepository extends JpaRepository<Accounting, Long> {
}
