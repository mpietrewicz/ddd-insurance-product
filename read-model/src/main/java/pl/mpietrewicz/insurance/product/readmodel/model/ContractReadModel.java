package pl.mpietrewicz.insurance.product.readmodel.model;

import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.InsuredId;

public interface ContractReadModel {

    ContractId getAggregateId();

    InsuredId getInsuredId();

}
