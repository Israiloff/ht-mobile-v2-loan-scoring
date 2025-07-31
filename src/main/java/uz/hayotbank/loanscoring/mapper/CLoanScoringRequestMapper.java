package uz.hayotbank.loanscoring.mapper;

import java.util.UUID;
import org.mapstruct.Mapper;
import uz.cbssolutions.commons.loan.obtain.models.camunda.FaceId;
import uz.hayotbank.camundarest.model.scoring.CLoanScoringRequest;

/**
 * Mapper interface for converting data to {@link CLoanScoringRequest}.
 *
 */
@Mapper(componentModel = "spring")
public interface CLoanScoringRequestMapper {

    /**
     * Maps the given arguments to a {@link CLoanScoringRequest}.
     *
     * @param applicationId The ID of the loan application.
     * @param faceId The FaceId object containing the URL and bucket.
     * @return A {@link CLoanScoringRequest} instance containing the application ID and FaceId.
     */
    CLoanScoringRequest map(UUID applicationId, FaceId faceId);
}
