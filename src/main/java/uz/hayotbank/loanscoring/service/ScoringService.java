
package uz.hayotbank.loanscoring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.cbssolutions.loan.enities.loan.Contact;
import uz.cbssolutions.loan.repository.ContactRepository;
import uz.cbssolutions.loan.repository.CreditApplicationRepository;
import uz.hayotbank.camundarest.CamundaRestService;
import uz.hayotbank.camundarest.scoring.CLoanScoringResponse;
import uz.hayotbank.loanscoring.exception.WrongApplicationStepException;
import uz.hayotbank.loanscoring.mapper.ContactMapper;
import uz.hayotbank.loanscoring.mapper.CreditAppMapper;
import uz.hayotbank.camundarest.scoring.CLoanScoringRequest;
import uz.hayotbank.loanscoring.model.ScoringRequest;

import java.util.Locale;
import java.util.UUID;

/**
 * Scoring service implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ScoringService {

    private final ContactRepository contactRepository;

    private final CreditApplicationRepository creditApplicationRepository;

    private final ContactMapper contactMapper;
    private final CreditAppMapper creditAppMapper;

    private final CamundaRestService camundaRestService;


    /**
     * Scores a loan application based on the provided request parameters. Validates the application step
     * and processes the provided contact details before initiating the loan scoring.
     *
     * @param request the scoring request containing loan application details, contact information, and additional
     *                parameters
     * @param userId  the unique identifier of the user initiating the scoring request
     * @param locale  the locale to be used for the scoring process
     * @return a reactive Mono containing the loan scoring response
     * @throws WrongApplicationStepException if the application step is invalid for scoring
     */
    public Mono<CLoanScoringResponse> score(ScoringRequest request, UUID userId, Locale locale) {
        log.debug("score started for request: {}", request);
        return creditApplicationRepository.findById(request.applicationId())
                .switchIfEmpty(Mono.error(WrongApplicationStepException::new))
                .filter(app -> app.step() == 2)
                .switchIfEmpty(Mono.error(WrongApplicationStepException::new))

                .flatMap(app ->
                        contactRepository.saveAll(
                                        Flux.fromIterable(request.contacts()).map(contactMapper::toEntity)
                                )
                                .map(Contact::id)
                                .collectList()
                                .map(contactIds -> creditAppMapper.toEntity(app, request, contactIds))
                                .flatMap(creditApplicationRepository::save)
                                .flatMap(creditApplication ->
                                        camundaRestService.scoreLoan(
                                                new CLoanScoringRequest(request.applicationId(), userId),
                                                locale
                                        )
                                ));

    }
}
