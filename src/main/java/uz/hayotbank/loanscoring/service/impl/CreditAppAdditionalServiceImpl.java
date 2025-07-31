package uz.hayotbank.loanscoring.service.impl;

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
import uz.hayotbank.loanscoring.service.ContactService;
import uz.hayotbank.loanscoring.service.CreditAppAdditionalService;

/**
 * {@inheritDoc}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CreditAppAdditionalServiceImpl implements CreditAppAdditionalService {

    private final ContactService contactService;

    private final CreditAppMapper creditAppMapper;

    private final CreditAppService creditAppService;

    private final ApplicationProperties applicationProperties;


    /**
     * {@inheritDoc}.
     */
    @Override
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
