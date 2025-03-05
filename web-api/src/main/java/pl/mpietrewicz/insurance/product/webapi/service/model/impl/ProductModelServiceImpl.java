package pl.mpietrewicz.insurance.product.webapi.service.model.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.product.readmodel.model.ProductReadModel;
import pl.mpietrewicz.insurance.product.readmodel.repository.ProductReadRepository;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.ProductNotFoundException;
import pl.mpietrewicz.insurance.product.webapi.dto.response.ProductModel;
import pl.mpietrewicz.insurance.product.webapi.dto.assembler.impl.ProductModelAssemblerImpl;
import pl.mpietrewicz.insurance.product.webapi.service.model.ProductModelService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductModelServiceImpl implements ProductModelService {

    private final ProductReadRepository productReadRepository;

    private final ProductModelAssemblerImpl productModelAssembler;

    @Override
    public ProductModel getBy(ProductId productId) {
        if (productId == null) {
            throw new IllegalArgumentException("productId cannot be null");
        }

        ProductReadModel productReadModel = productReadRepository.findByAggregateId(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        return productModelAssembler.toModel(productReadModel);
    }

    @Override
    public CollectionModel<ProductModel> getAll() {
        List<ProductReadModel> productReadModels = productReadRepository.findAllProjectedBy();
        return productModelAssembler.toModel(productReadModels);
    }

}
