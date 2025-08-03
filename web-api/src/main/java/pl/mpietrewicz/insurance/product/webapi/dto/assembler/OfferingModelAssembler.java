package pl.mpietrewicz.insurance.product.webapi.dto.assembler;

import org.springframework.hateoas.CollectionModel;
import pl.mpietrewicz.insurance.product.readmodel.model.OfferingReadModel;
import pl.mpietrewicz.insurance.product.webapi.dto.response.OfferingModel;

import java.util.List;

public interface OfferingModelAssembler {

    OfferingModel toModel(OfferingReadModel entity);

    CollectionModel<OfferingModel> toModel(List<OfferingReadModel> entities);

}
