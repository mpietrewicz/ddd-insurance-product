package pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage;

import jakarta.persistence.Embeddable;
import pl.mpietrewicz.insurance.ddd.annotations.domain.PublishedLanguage;

import java.util.UUID;

@PublishedLanguage
@Embeddable
public class ContractId extends AggregateId<ContractId> {

	public ContractId(String aggregateId) {
		super(aggregateId);
	}

	protected ContractId() {
		super();
	}
	
	public static ContractId generate(){
		return new ContractId(UUID.randomUUID().toString());
	}

}