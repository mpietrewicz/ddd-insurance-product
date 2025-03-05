package pl.mpietrewicz.insurance.product.webapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.ProductNotFoundException;
import pl.mpietrewicz.insurance.product.webapi.service.adapter.ProductServiceAdapter;
import pl.mpietrewicz.insurance.product.webapi.dto.request.CreateProductRequest;
import pl.mpietrewicz.insurance.product.webapi.dto.response.ProductModel;
import pl.mpietrewicz.insurance.product.webapi.service.model.ProductModelService;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Managing insurance products")
public class ProductController {

    private final ProductModelService productModelService;

    private final ProductServiceAdapter productServiceAdapter;

    @Operation(summary = "Get all available products",
            description = "Returns a list of all insurance products with HATEOAS links.")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
            content = @Content(mediaType = "application/hal+json",
                    schema = @Schema(implementation = ProductModel.class)))
    @GetMapping
    public CollectionModel<ProductModel> getProducts() {
        CollectionModel<ProductModel> productsModel = productModelService.getAll();
        productsModel.forEach(productModel ->
                productModel.add(getLinkToGetProduct(productModel.getId())));
        productsModel.add(getLinkToGetProducts());
        productsModel.add(getLinkToCreateProducts());

        return productsModel;
    }

    @Operation(summary = "Get product by ID", description = "Returns a single product based on its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ProductModel.class))),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{productId}")
    public ProductModel getProduct(@PathVariable ProductId productId) {
        return productModelService.getBy(productId);
    }

    @Operation(summary = "Create a new product",
            description = "Creates a new insurance product and returns the location header.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created")
    })
    @PostMapping
    public ResponseEntity<RepresentationModel<?>> createProduct(@Valid @RequestBody CreateProductRequest createProductRequest) {
        ProductId productId = productServiceAdapter.createProduct(createProductRequest);

        URI productLocation = getLinkToGetProduct(productId).toUri();
        return ResponseEntity.created(productLocation).build();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Void> handleNotFound(ProductNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    private static Link getLinkToGetProduct(ProductId productId) {
        return linkTo(methodOn(ProductController.class)
                .getProduct(productId)).withSelfRel();
    }

    private static Link getLinkToGetProducts() {
        return linkTo(methodOn(ProductController.class)
                .getProducts()).withSelfRel();
    }

    private static Link getLinkToCreateProducts() {
        return linkTo(methodOn(ProductController.class)
                .createProduct(null)).withRel("create-product");
    }

}
