package uz.hayotbank.loanscoring.service;

import reactor.core.publisher.Mono;
import uz.cbssolutions.commons.loan.obtain.models.camunda.FaceId;

import java.util.UUID;

/**
 * FaceId service implementation.
 */

public interface FaceIdService {

    /**
     * Saves the FaceId information extracted from the provided content.
     *
     * @param content The base64-encoded string representing the file content to be saved.
     * @param userId The unique identifier of the user associated with the FaceId.
     * @param applicationId The unique identifier of the application for which the FaceId is being
     *        saved.
     * @return A {@link Mono} emitting the saved {@link FaceId} instance if successful, or an error
     *         if the operation
     *         fails or results in an empty response.
     */
    Mono<FaceId> save(String content, String userId, UUID applicationId);
}
