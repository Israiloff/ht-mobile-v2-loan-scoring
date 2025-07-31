package uz.hayotbank.camundarest.model.scoring;

import java.io.Serializable;
import java.util.UUID;
import uz.cbssolutions.commons.loan.obtain.models.camunda.FaceId;

/**
 * Camunda scoring request model.
 *
 * @param applicationId Loan application ID.
 * @param faceId Face ID object containing the URL and bucket.
 */
public record CLoanScoringRequest(UUID applicationId, FaceId faceId) implements Serializable {
}
