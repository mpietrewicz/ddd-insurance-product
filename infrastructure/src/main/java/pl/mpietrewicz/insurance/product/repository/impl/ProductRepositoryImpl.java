package pl.mpietrewicz.insurance.product.repository.impl;

import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainRepositoryImpl;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.support.infrastructure.repo.GenericJpaRepository;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.repository.ProductRepository;

@DomainRepositoryImpl
public class ProductRepositoryImpl extends GenericJpaRepository<Product, ProductId> implements ProductRepository {

}