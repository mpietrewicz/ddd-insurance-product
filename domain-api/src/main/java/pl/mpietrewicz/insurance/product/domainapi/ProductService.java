package pl.mpietrewicz.insurance.product.domainapi;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.ProductData;

public interface ProductService {

    /**
     * Creates a new product based on the provided product data and persists it in the repository.
     *
     * @param productData the data used to create the product
     * @return the {@link ProductId} of the newly created product
     */
    ProductId createProduct(ProductData productData);

}