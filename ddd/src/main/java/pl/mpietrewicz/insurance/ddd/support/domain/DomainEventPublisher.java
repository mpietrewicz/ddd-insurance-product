
package pl.mpietrewicz.insurance.ddd.support.domain;

import java.io.Serializable;

public interface DomainEventPublisher {

    void publish(Serializable event);

}