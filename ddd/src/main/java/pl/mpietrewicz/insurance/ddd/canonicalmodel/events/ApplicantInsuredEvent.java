package pl.mpietrewicz.insurance.ddd.canonicalmodel.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.event.Event;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.InsuredId;

@Event(boundedContext = "product")
@Getter
@RequiredArgsConstructor
public class ApplicantInsuredEvent {

    private final ApplicantId applicantId;
    private final InsuredId insuredId;

}