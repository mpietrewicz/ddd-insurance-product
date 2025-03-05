package pl.mpietrewicz.insurance.product.webapi.dto.assembler;

import org.springframework.hateoas.CollectionModel;
import pl.mpietrewicz.insurance.product.readmodel.model.OfferReadModel;
import pl.mpietrewicz.insurance.product.webapi.dto.response.OfferModel;

import java.util.List;

public interface OfferModelAssembler {

    OfferModel toModel(OfferReadModel entity);

    CollectionModel<OfferModel> toModel(List<OfferReadModel> entities);

}
