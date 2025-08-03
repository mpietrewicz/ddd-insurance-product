package pl.mpietrewicz.insurance.ddd.sharedkernel.exception;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;

import java.util.NoSuchElementException;

public class ProductNotFoundException extends NoSuchElementException {

    public ProductNotFoundException(ProductId productId) {
        super("Product not found for id: " + productId);
    }

}
