package pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage;

import jakarta.persistence.Embeddable;
import pl.mpietrewicz.insurance.ddd.annotations.domain.PublishedLanguage;

import java.util.UUID;

@PublishedLanguage
@Embeddable
public class InsuredId extends AggregateId<InsuredId> {

	public InsuredId(String aggregateId) {
		super(aggregateId);
	}

	public InsuredId(ApplicantId applicantId) {
		super(applicantId.getId());
	}

	protected InsuredId() {
		super();
	}

	public static InsuredId generate(){
		return new InsuredId(UUID.randomUUID().toString());
	}

}