package pl.mpietrewicz.insurance.product.readmodel.model;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;

import java.time.LocalDate;

public interface ComponentReadModel {

    ProductId getProductId();

    LocalDate getStartDate();

    LocalDate getEndDate();

}
