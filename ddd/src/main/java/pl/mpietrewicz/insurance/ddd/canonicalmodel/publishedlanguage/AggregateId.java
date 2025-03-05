package pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

import static java.util.Objects.requireNonNull;

@MappedSuperclass
public abstract class AggregateId<T extends AggregateId<T>> implements Serializable {

	private String aggregateId;

	public AggregateId(String aggregateId) {
		requireNonNull(aggregateId);
		this.aggregateId = aggregateId;
	}

	protected AggregateId() {
	}
	
	@JsonValue
	public String getId() {
		return aggregateId;
	}

	@Override
	public int hashCode() {
		return aggregateId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AggregateId<?> other = (AggregateId<?>) obj;
		if (aggregateId == null) {
			if (other.aggregateId != null)
				return false;
		} else if (!aggregateId.equals(other.aggregateId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return aggregateId;
	}

}