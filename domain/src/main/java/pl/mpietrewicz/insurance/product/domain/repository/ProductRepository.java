package pl.mpietrewicz.insurance.product.domain.repository;

import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainRepository;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;

import java.util.List;
import java.util.Optional;

@DomainRepository
public interface ProductRepository {

    Optional<Product> load(ProductId productId);

    void save(Product product);

    List<Product> loadAll();

}