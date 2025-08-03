package pl.mpietrewicz.insurance.product.domain.api.service;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.application.ApplicationService;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.agregate.product.ProductFactory;
import pl.mpietrewicz.insurance.product.domain.repository.ProductRepository;
import pl.mpietrewicz.insurance.product.domainapi.ProductService;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.ProductData;

@ApplicationService
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductFactory productFactory;

    private final ProductRepository productRepository;

    @Override
    public ProductId createProduct(ProductData productData) {
        Product product = productFactory.create(productData);
        productRepository.save(product);
        return product.getAggregateId();
    }

}