package uz.hayotbank.camundarest.scoring;

import java.io.Serializable;
import java.util.UUID;

/**
 * Camunda scoring request model.
 *
 * @param applicationId Loan application ID.
 * @param userId        user ID.
 */
public record CLoanScoringRequest(UUID applicationId, UUID userId) implements Serializable {
}
