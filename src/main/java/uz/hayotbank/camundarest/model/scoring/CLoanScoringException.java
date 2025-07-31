
package uz.hayotbank.camundarest.model.scoring;

import lombok.Getter;
import uz.hayotbank.commons.error.models.DecodableException;
import uz.hayotbank.loanscoring.config.Constants;

/**
 * The CLoanScoringException class represents a custom exception specific to the
 * loan scoring process. This exception extends the DecodableException class and
 * includes additional information encapsulated within a CLoanScoringResponse object.
 *
 * This exception is used to handle scenarios where loan scoring operations encounter
 * issues, and it provides details about the failure, including response code and
 * success status.
 */
@Getter
public class CLoanScoringException extends DecodableException {

    private final CLoanScoringResponse response;

    /**
     * Constructs a new CLoanScoringException with the specified CLoanScoringResponse.
     *
     * @param response the CLoanScoringResponse containing the response details, including
     *                 the code and success status.
     */
    public CLoanScoringException(CLoanScoringResponse response) {
        super(Constants.ACTOR, response.code());
        this.response = response;
    }
}
