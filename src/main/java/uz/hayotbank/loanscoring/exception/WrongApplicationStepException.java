package uz.hayotbank.loanscoring.exception;

import uz.hayotbank.commons.error.models.DecodableException;
import uz.hayotbank.loanscoring.config.Constants;

/**
 * This exception is thrown when the application is in an invalid step or state for the current operation.
 * It indicates that the requested processing cannot proceed due to the application's step not meeting
 * the required conditions.
 *
 * This exception may typically be used in validation scenarios where the step of a loan application or
 * similar process must adhere to specific criteria before proceeding.
 *
 * The exception leverages predefined constants and error codes to ensure consistency and meaningful
 * error reporting in the application.
 */
public class WrongApplicationStepException extends DecodableException {

    /**
     * Constructs a new WrongApplicationStepException.
     *
     * This exception is thrown when a loan application is in an invalid step or state for the
     * requested operation. The exception uses predefined constants to ensure consistent
     * error messaging across the application.
     *
     * The error is identified with:
     * - Actor: "loanScoring".
     * - Error Code: "1001".
     * - Message: "Wrong application step".
     */
    public WrongApplicationStepException() {
        super(Constants.ACTOR, "1001", "Wrong application step");
    }
}
