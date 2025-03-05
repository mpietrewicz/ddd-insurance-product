package pl.mpietrewicz.insurance.product.webapi.service.adapter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.product.domainapi.ProductService;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.ProductData;
import pl.mpietrewicz.insurance.product.webapi.dto.converter.ProductDataConverter;
import pl.mpietrewicz.insurance.product.webapi.dto.request.CreateProductRequest;
import pl.mpietrewicz.insurance.product.webapi.service.adapter.ProductServiceAdapter;

@Service
@RequiredArgsConstructor
public class ProductServiceAdapterImpl implements ProductServiceAdapter {

    private final ProductDataConverter productDataConverter;

    private final ProductService productService;

    @Override
    public ProductId createProduct(CreateProductRequest createProductRequest) {
        ProductData productData = productDataConverter.convert(createProductRequest);
        return productService.createProduct(productData);
    }

}
