
package uz.hayotbank.camundarest.scoring;

import java.io.Serializable;

/**
 * Represents the response of a loan scoring operation.
 *
 * This class encapsulates key information about the outcome of a loan scoring request,
 * including a response code and a success indicator.
 *
 * It is primarily designed to be immutable and serializable, ensuring consistent
 * behavior during inter-process communication or when dealing with external services.
 *
 * Fields:
 *@param code `code`: A string representing the code that provides additional information
 *   about the scoring result (e.g., error code or success indicator).
 *@param success `success`: A boolean indicating whether the loan scoring operation was
 *   successful (`true`) or failed (`false`).
 */
public record CLoanScoringResponse(String code, Boolean success) implements Serializable {
}
