package pl.mpietrewicz.insurance.ddd.support.infrastructure.repo;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Slawek
 * 
 */
@Component
@Scope("prototype")//created in domain factories, not in spring container, therefore we don't want eager creation
@MappedSuperclass
public abstract class BaseAggregateRoot<T> {
	public static enum AggregateStatus {
		ACTIVE, ARCHIVE
	}

	@EmbeddedId
	@AttributeOverrides({
		  @AttributeOverride(name = "idValue", column = @Column(name = "aggregateId", nullable = false))})
	public T aggregateId;

	@Version
	public Long version;

	@Enumerated(EnumType.ORDINAL)
	private AggregateStatus aggregateStatus = AggregateStatus.ACTIVE;
	
	public void markAsRemoved() {
		aggregateStatus = AggregateStatus.ARCHIVE;
	}

	public T getAggregateId() {
		return aggregateId;
	}

	public Long getVersion() {
		return version;
	}

	public boolean isRemoved() {
		return aggregateStatus == AggregateStatus.ARCHIVE;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof BaseAggregateRoot) {
			BaseAggregateRoot other = (BaseAggregateRoot) obj;
			if (other.aggregateId == null)
				return false;
			return other.aggregateId.equals(aggregateId);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {	
		return aggregateId.hashCode();
	}
}