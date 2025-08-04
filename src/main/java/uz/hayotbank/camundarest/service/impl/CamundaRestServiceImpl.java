package uz.hayotbank.camundarest.service.impl;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import uz.hayotbank.camundarest.config.CamundaRestConfig;
import uz.hayotbank.camundarest.config.CamundaRestProperties;
import uz.hayotbank.camundarest.model.scoring.CLoanScoringException;
import uz.hayotbank.camundarest.model.scoring.CLoanScoringRequest;
import uz.hayotbank.camundarest.model.scoring.CLoanScoringResponse;
import uz.hayotbank.camundarest.service.CamundaRestService;
import uz.hayotbank.loanscoring.config.Constants;

/**
 * {@inheritDoc}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CamundaRestServiceImpl implements CamundaRestService {

    @Qualifier(CamundaRestConfig.WEB_CLIENT)
    private final WebClient webClient;
    private final CamundaRestProperties properties;

    /**
     * {@inheritDoc}.
     */
    @Override
    public Mono<CLoanScoringResponse> scoreLoan(CLoanScoringRequest request, Locale locale) {
        log.debug("scoreLoan started for request: {}", request);
        return webClient.post()
                .uri(properties.loanScoringEndpoint())
                .header(Constants.HEADER_KEY_LOCALE, locale.toLanguageTag())
                .bodyValue(request)
                .retrieve()
                .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(), this::handleError)
                .bodyToMono(CLoanScoringResponse.class)
                .flatMap(response ->
                        response.success()
                                ?
                                Mono.just(response)
                                :
                                Mono.error(new CLoanScoringException(response))
                )
                .doOnNext(response -> log.debug("scoreLoan response: {}", response));
    }

    /**
     * Handles errors during the client response processing by mapping the response body
     * to a {@code CLoanScoringException}.
     *
     * @param clientResponse The client response containing the error details, which will be
     *                       processed into a {@code CLoanScoringException}.
     * @return A {@code Mono<CLoanScoringException>} that contains the exception built from
     * the client response.
     */
    private Mono<CLoanScoringException> handleError(ClientResponse clientResponse) {
        return clientResponse
                .bodyToMono(CLoanScoringResponse.class)
                .map(CLoanScoringException::new);
    }


}
