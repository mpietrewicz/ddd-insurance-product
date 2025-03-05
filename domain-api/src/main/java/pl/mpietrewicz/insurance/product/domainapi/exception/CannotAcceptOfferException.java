package pl.mpietrewicz.insurance.product.domainapi.exception;

/**
 * Exception indicating an attempt to accept an offer that does not meet the acceptance conditions.
 * Thrown when:
 * <ul>
 *   <li>the offer has already been accepted,</li>
 *   <li>the offer does not contain any products,</li>
 *   <li>the acceptance date is on or after the offer start date.</li>
 * </ul>
 */
public class CannotAcceptOfferException extends IllegalStateException {

    public CannotAcceptOfferException() {
        super("Cannot accept offer");
    }

}
