package uz.hayotbank.camundarest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import uz.hayotbank.camundarest.scoring.CLoanScoringException;
import uz.hayotbank.camundarest.scoring.CLoanScoringRequest;
import uz.hayotbank.camundarest.scoring.CLoanScoringResponse;
import uz.hayotbank.commons.error.handlers.config.Constants;

import java.util.Locale;

/**
 * Service class for interacting with the Camunda REST API.
 * This class is annotated with @Service to indicate that it is a
 * Spring-managed service component within the application context.
 * <p>
 * It uses the @RequiredArgsConstructor annotation to generate
 * a constructor for final fields, enabling dependency injection.
 * <p>
 * The @Slf4j annotation facilitates logging within this class,
 * streamlining the handling of log outputs for tracking and debugging operations
 * related to Camunda REST API communications.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CamundaRestService {

    @Qualifier(CamundaRestConfig.WEB_CLIENT)
    private final WebClient webClient;
    private final CamundaRestProperties properties;

    /**
     * Sends a loan scoring request to an external service and processes the response.
     * This method interacts with a remote API to perform loan scoring based on the provided
     * request data and locale information.
     *
     * @param request The loan scoring request object containing the necessary details such as the application ID.
     * @param locale  The locale information to customize the request or handle any locale-specific aspects.
     * @return A {@code Mono<CLoanScoringResponse>} that emits the loan scoring response if successful,
     * or an error if the scoring process fails.
     */
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
