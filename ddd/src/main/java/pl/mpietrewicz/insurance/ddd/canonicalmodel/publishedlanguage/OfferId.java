package pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage;

import jakarta.persistence.Embeddable;
import pl.mpietrewicz.insurance.ddd.annotations.domain.PublishedLanguage;

import java.util.UUID;

@PublishedLanguage
@Embeddable
public class OfferId extends AggregateId<OfferId> {

	public OfferId(String aggregateId) {
		super(aggregateId);
	}

	protected OfferId() {
		super();
	}

	public static OfferId generate(){
		return new OfferId(UUID.randomUUID().toString());
	}

}