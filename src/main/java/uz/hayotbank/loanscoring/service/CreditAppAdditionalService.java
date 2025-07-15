package uz.hayotbank.loanscoring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import uz.cbssolutions.commons.loan.obtain.models.scoring.ScoringRequest;
import uz.cbssolutions.commons.loan.obtain.utils.service.CreditAppService;
import uz.cbssolutions.loan.enities.loan.Contact;
import uz.cbssolutions.loan.enities.loan.CreditApplication;
import uz.hayotbank.loanscoring.config.ApplicationProperties;
import uz.hayotbank.loanscoring.config.Constants;
import uz.hayotbank.loanscoring.mapper.CreditAppMapper;

/**
 * Credit application additional service implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CreditAppAdditionalService {

    private final ContactService contactService;

    private final CreditAppMapper creditAppMapper;

    private final CreditAppService creditAppService;

    private final ApplicationProperties applicationProperties;


    /**
     * Updates a credit application based on the provided scoring request.
     * The method retrieves the application by its ID and step, updates its contact information
     * and other details, and then saves the updated application.
     *
     * @param request the scoring request containing application ID, contacts, and related data
     * @return a Mono emitting the updated CreditApplication instance
     */
    @Transactional
    public Mono<CreditApplication> update(ScoringRequest request) {
        log.debug("updateCreditApplication started for request: {}", request);
        return creditAppService
                .findByIdAndStep(request.applicationId(),
                        applicationProperties.scoringStepNumber() - 1,
                        Constants.ACTOR
                )
                .flatMap(app ->
                        contactService
                                .saveAll(request.contacts(), app.id())
                                .map(Contact::id)
                                .collectList()
                                .map(contactIds -> request.isRegularAddress()
                                        ?
                                        creditAppMapper.toEntity(app, contactIds)
                                        :
                                        creditAppMapper.toEntity(app, request, contactIds)))
                .flatMap(creditApplication -> creditAppService.save(creditApplication, Constants.ACTOR));
    }

}
