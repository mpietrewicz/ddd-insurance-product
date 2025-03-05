package pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage;

import jakarta.persistence.Embeddable;
import pl.mpietrewicz.insurance.ddd.annotations.domain.PublishedLanguage;

import java.util.UUID;

@PublishedLanguage
@Embeddable
public class ProductId extends AggregateId<ProductId> {

	public ProductId(String aggregateId) {
		super(aggregateId);
	}

	protected ProductId() {
		super();
	}

	public static ProductId generate(){
		return new ProductId(UUID.randomUUID().toString());
	}

}