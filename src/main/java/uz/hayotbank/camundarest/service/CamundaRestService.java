package uz.hayotbank.camundarest.service;

import reactor.core.publisher.Mono;
import uz.hayotbank.camundarest.model.scoring.CLoanScoringRequest;
import uz.hayotbank.camundarest.model.scoring.CLoanScoringResponse;

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

public interface CamundaRestService {


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
    Mono<CLoanScoringResponse> scoreLoan(CLoanScoringRequest request, Locale locale);

}
