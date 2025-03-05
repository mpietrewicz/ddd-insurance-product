package pl.mpietrewicz.insurance.product.webapi.service.adapter;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.product.webapi.dto.request.CreateProductRequest;

public interface ProductServiceAdapter {

    ProductId createProduct(CreateProductRequest createProductRequest);

}
