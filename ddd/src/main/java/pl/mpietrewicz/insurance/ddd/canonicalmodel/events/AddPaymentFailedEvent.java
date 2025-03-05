package pl.mpietrewicz.insurance.ddd.canonicalmodel.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.event.Event;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.AggregateId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;

@Event(boundedContext = "balance")
@Getter
@RequiredArgsConstructor
public class AddPaymentFailedEvent implements BalanceOperationFailedEvent {

    private final ContractId contractId;
    private final String message;

}