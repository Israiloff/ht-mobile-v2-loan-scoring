package uz.hayotbank.loanscoring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import uz.cbssolutions.commons.loan.obtain.models.scoring.ScoringRequest;
import uz.hayotbank.camundarest.CamundaRestService;
import uz.hayotbank.camundarest.scoring.CLoanScoringRequest;
import uz.hayotbank.camundarest.scoring.CLoanScoringResponse;

import java.util.Locale;

/**
 * Scoring service implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ScoringService {

    private final CreditAppAdditionalService additionalService;
    private final CamundaRestService camundaRestService;

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
    public Mono<CLoanScoringResponse> score(ScoringRequest request, Locale locale) {
        log.debug("score started for request: {}", request);
        return additionalService.update(request)
                .flatMap(app -> camundaRestService.scoreLoan(new CLoanScoringRequest(app.id()), locale));
    }

}
