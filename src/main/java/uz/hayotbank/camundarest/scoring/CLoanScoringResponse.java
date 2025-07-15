
package uz.hayotbank.camundarest.scoring;

import java.io.Serializable;

/**
 * Represents the response of a loan scoring operation.
 * <p>
 * This class encapsulates key information about the outcome of a loan scoring request,
 * including a response code and a success indicator.
 * <p>
 * It is primarily designed to be immutable and serializable, ensuring consistent
 * behavior during inter-process communication or when dealing with external services.
 * <p>
 * Fields:
 *
 * @param code    `code`: A string representing the code that provides additional information
 *                about the scoring result (e.g., error code or success indicator).
 * @param success `success`: A boolean indicating whether the loan scoring operation was
 *                successful (`true`) or failed (`false`).
 * @param instanceId instanceId
 */
public record CLoanScoringResponse(String code, Boolean success, String instanceId) implements Serializable {
}
