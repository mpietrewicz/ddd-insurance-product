package pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage;

import jakarta.persistence.Embeddable;
import pl.mpietrewicz.insurance.ddd.annotations.domain.PublishedLanguage;

import java.util.UUID;

@PublishedLanguage
@Embeddable
public class ApplicantId extends AggregateId<ApplicantId> {

	public ApplicantId(String aggregateId) {
		super(aggregateId);
	}

	protected ApplicantId() {
		super();
	}

	public static ApplicantId generate(){
		return new ApplicantId(UUID.randomUUID().toString());
	}

}