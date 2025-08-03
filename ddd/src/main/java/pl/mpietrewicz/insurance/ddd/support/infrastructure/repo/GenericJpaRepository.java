
package pl.mpietrewicz.insurance.ddd.support.infrastructure.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.AggregateId;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

/**
 * @author Slawek
 */
public class GenericJpaRepository<A extends BaseAggregateRoot<ID>, ID extends AggregateId<ID>> {

    @PersistenceContext
    public EntityManager entityManager;

    private Class<A> clazz;

    @SuppressWarnings("unchecked")
    public GenericJpaRepository() {
        this.clazz = ((Class<A>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public Optional<A> load(ID id) {
        // lock to be sure when creating other objects based on values of this aggregate
        return Optional.ofNullable(
                entityManager.find(clazz, id, LockModeType.OPTIMISTIC)
        );
    }

    public List<A> loadAll() {
        String jpql = "SELECT a FROM " + clazz.getSimpleName() + " a";
        return entityManager.createQuery(jpql, clazz).getResultList();
    }

    public void save(A aggregate) {
        if (entityManager.contains(aggregate)) {
            // locking Aggregate Root logically protects whole aggregate
            entityManager.lock(aggregate, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        } else {
            entityManager.persist(aggregate);
        }
    }

    public void delete(ID id) {
        Optional<A> entity = load(id);
        // just flag
        entity.ifPresent(A::markAsRemoved);
    }

}