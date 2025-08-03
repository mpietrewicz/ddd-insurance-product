package pl.mpietrewicz.insurance.product.readmodel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mpietrewicz.insurance.ddd.annotations.application.Reader;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.readmodel.model.ProductReadModel;

import java.util.List;
import java.util.Optional;

@Reader
public interface ProductReadRepository extends JpaRepository<Product, Long> {

    Optional<ProductReadModel> findByAggregateId(ProductId productId);

    List<ProductReadModel> findAllProjectedBy();

}
