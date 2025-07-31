package uz.hayotbank.loanscoring.service;

import reactor.core.publisher.Mono;
import uz.cbssolutions.commons.loan.obtain.models.scoring.ScoringRequest;
import uz.hayotbank.camundarest.model.scoring.CLoanScoringResponse;

import java.util.Locale;

/**
 * Scoring service implementation.
 */

public interface ScoringService {

    /**
     * Processes a loan scoring request by updating the credit application and
     * sending the scoring data to an external service.
     * <p>
     * This method first updates the credit application details using the provided
     * scoring request. Then, it prepares a new loan scoring request and invokes
     * an external service to perform loan scoring. The result is encapsulated
     * in a {@code Mono<CLoanScoringResponse>} object.
     *
     * @param request the scoring request containing application details such as ID,
     *                contacts, and other necessary data for scoring
     * @param locale  the locale information used for regional customization of the
     *                scoring operation
     * @return a {@code Mono<CLoanScoringResponse>} containing the result of the loan
     * scoring operation
     */
    Mono<CLoanScoringResponse> score(ScoringRequest request, Locale locale);

}
