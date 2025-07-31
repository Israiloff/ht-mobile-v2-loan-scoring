package uz.hayotbank.loanscoring.service;

import reactor.core.publisher.Mono;
import uz.cbssolutions.commons.loan.obtain.models.scoring.ScoringRequest;
import uz.cbssolutions.loan.enities.loan.CreditApplication;

/**
 * Credit application additional service implementation.
 */

public interface CreditAppAdditionalService {


    /**
     * Updates a credit application based on the provided scoring request.
     * The method retrieves the application by its ID and step, updates its contact information
     * and other details, and then saves the updated application.
     *
     * @param request the scoring request containing application ID, contacts, and related data
     * @return a Mono emitting the updated CreditApplication instance
     */
    Mono<CreditApplication> update(ScoringRequest request);

}
