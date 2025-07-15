package uz.hayotbank.loanscoring.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Represents a request for loan application scoring.
 * This record encapsulates the necessary details required for scoring a loan application.
 * It is used as input in the scoring process to evaluate the loan application.
 *
 * @param applicationId     The unique identifier of the loan application.
 * @param region            The region associated with the loan application.
 * @param district          The district associated with the loan application.
 * @param address           The detailed address associated with the loan application.
 * @param homeNumber        The home number of the applicant's address.
 * @param purpose           The purpose of the loan application, represented as a numeric value.
 * @param contacts          A list of contact details associated with the loan application.
 * @param cardIds           A list of card identifiers associated with the loan application.
 * @param isRegularAddress  Indicates whether the provided address is a regular address or not.
 */
public record ScoringRequest(
        UUID applicationId,
        String region,
        String district,
        String address,
        String homeNumber,
        Long purpose,
        List<ContactModel> contacts,
        List<UUID> cardIds,
        Boolean isRegularAddress) implements Serializable {
}
