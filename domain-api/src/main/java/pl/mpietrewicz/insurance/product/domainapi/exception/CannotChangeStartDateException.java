package pl.mpietrewicz.insurance.product.domainapi.exception;

/**
 * Exception indicating an attempt to change the start date of an insurance offer in violation of business rules.
 * Thrown when:
 * <ul>
 *   <li>the offer has already been accepted, or</li>
 *   <li>the start date of the offer is equal to or later than the accounting date.</li>
 * </ul>
 */
public class CannotChangeStartDateException extends IllegalStateException {

    public CannotChangeStartDateException() {
        super("Cannot change start date of the offer");
    }

}
