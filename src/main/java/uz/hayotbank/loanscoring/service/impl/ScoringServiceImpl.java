package uz.hayotbank.loanscoring.service.impl;

import java.util.Locale;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import uz.cbssolutions.commons.loan.obtain.models.scoring.ScoringRequest;
import uz.hayotbank.camundarest.model.scoring.CLoanScoringResponse;
import uz.hayotbank.camundarest.service.CamundaRestService;
import uz.hayotbank.commons.security.core.model.UserDetails;
import uz.hayotbank.loanscoring.mapper.CLoanScoringRequestMapper;
import uz.hayotbank.loanscoring.service.CreditAppAdditionalService;
import uz.hayotbank.loanscoring.service.FaceIdService;
import uz.hayotbank.loanscoring.service.ScoringService;

/**
 * {@inheritDoc}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ScoringServiceImpl implements ScoringService {

    private final CreditAppAdditionalService additionalService;
    private final CamundaRestService camundaRestService;
    private final FaceIdService faceIdService;
    private final Mono<UserDetails> userPublisher;
    private final CLoanScoringRequestMapper requestMapper;

    /**
     * {@inheritDoc}.
     */
    @Override
    public Mono<CLoanScoringResponse> score(ScoringRequest request, Locale locale) {
        log.debug("score started for request: {}", request);
        return userPublisher
                .flatMap(user -> faceIdService.save(request.faceImage(), user.getId().toString(),
                        request.applicationId()))
                .zipWith(additionalService.update(request))
                .map(pair -> requestMapper.map(pair.getT2().id(), pair.getT1()))
                .flatMap(cr -> camundaRestService.scoreLoan(cr, locale));
    }

}
