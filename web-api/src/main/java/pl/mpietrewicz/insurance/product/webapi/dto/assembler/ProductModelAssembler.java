package pl.mpietrewicz.insurance.product.webapi.dto.assembler;

import org.springframework.hateoas.CollectionModel;
import pl.mpietrewicz.insurance.product.readmodel.model.ProductReadModel;
import pl.mpietrewicz.insurance.product.webapi.dto.response.ProductModel;

import java.util.List;

public interface ProductModelAssembler {

    ProductModel toModel(ProductReadModel entity);

    CollectionModel<ProductModel> toModel(List<ProductReadModel> entities);

}
