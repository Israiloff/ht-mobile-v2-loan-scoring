package uz.hayotbank.loanscoring.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Scoring result model.
 *
 * @param applicationId  Loan application ID.
 * @param approved       Approved flag.
 * @param approvedAmount Approved amount.
 * @param paymentAmount  Payment amount.
 * @param loanTime       Loan time.
 * @param loanRate       Loan rate.
 */
public record ScoringResult(
        UUID applicationId,
        Boolean approved,
        Double approvedAmount,
        Double paymentAmount,
        Long loanTime,
        Double loanRate) implements Serializable {
}
