package uz.hayotbank.loanscoring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import uz.cbssolutions.commons.loan.obtain.models.scoring.ScoringRequest;
import uz.hayotbank.camundarest.model.scoring.CLoanScoringRequest;
import uz.hayotbank.camundarest.model.scoring.CLoanScoringResponse;
import uz.hayotbank.camundarest.service.CamundaRestService;
import uz.hayotbank.loanscoring.service.CreditAppAdditionalService;
import uz.hayotbank.loanscoring.service.ScoringService;

import java.util.Locale;

/**
 * {@inheritDoc}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ScoringServiceImpl implements ScoringService {

    private final CreditAppAdditionalService additionalService;
    private final CamundaRestService camundaRestService;

    /**
     * {@inheritDoc}.
     */
    @Override
    public Mono<CLoanScoringResponse> score(ScoringRequest request, Locale locale) {
        log.debug("score started for request: {}", request);
        return additionalService.update(request)
                .flatMap(app -> camundaRestService.scoreLoan(new CLoanScoringRequest(app.id()), locale));
    }

}
