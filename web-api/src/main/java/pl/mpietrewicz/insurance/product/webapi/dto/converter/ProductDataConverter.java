package pl.mpietrewicz.insurance.product.webapi.dto.converter;

import pl.mpietrewicz.insurance.product.domainapi.dto.product.ProductData;
import pl.mpietrewicz.insurance.product.webapi.dto.request.CreateProductRequest;

public interface ProductDataConverter {

    ProductData convert(CreateProductRequest createProductRequest);

}
