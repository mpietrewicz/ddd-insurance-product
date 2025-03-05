package pl.mpietrewicz.insurance.product.webapi.service.model;

import org.springframework.hateoas.CollectionModel;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.ProductNotFoundException;
import pl.mpietrewicz.insurance.product.webapi.dto.response.ProductModel;

public interface ProductModelService {

    /**
     * Retrieves a ProductModel by its unique identifier.
     *
     * @param productId the unique identifier of the product to retrieve, must not be null
     * @return the ProductModel corresponding to the provided identifier, never null
     * @throws ProductNotFoundException if no product exists with the given identifier
     * @throws IllegalArgumentException if productId is null
     */
    ProductModel getBy(ProductId productId);

    /**
     * Retrieves all available ProductModel objects.
     *
     * @return a CollectionModel containing all ProductModel objects, never null
     */
    CollectionModel<ProductModel> getAll();

}