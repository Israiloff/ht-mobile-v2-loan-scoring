package uz.hayotbank.loanscoring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.cbssolutions.commons.loan.obtain.models.scoring.ScoringRequest;
import uz.cbssolutions.loan.enities.loan.CreditApplication;

import java.util.List;
import java.util.UUID;

/**
 * Credit application entity mapper.
 */
@Mapper(componentModel = "spring")
public interface CreditAppMapper {

    /**
     * Maps a credit application and scoring request to a new credit application entity.
     *
     * @param app The source credit application containing initial data.
     * @param request The scoring request containing additional address and region information.
     * @param contactIds A list of contact IDs associated with the credit application.
     * @return A new credit application entity constructed from the given parameters.
     */
    @Mapping(target = "id", source = "app.id")
    @Mapping(target = "status", source = "app.status")
    @Mapping(target = "pinfl", source = "app.pinfl")
    @Mapping(target = "claimId", source = "app.claimId")
    @Mapping(target = "currencyId", source = "app.currencyId")
    @Mapping(target = "exchangeRates", source = "app.exchangeRates")
    @Mapping(target = "scoringSolutionId", source = "app.scoringSolutionId")
    @Mapping(target = "loanRate", source = "app.loanRate")
    @Mapping(target = "appType", source = "app.appType")
    @Mapping(target = "productType", source = "app.productType")
    @Mapping(target = "approvedAmount", source = "app.approvedAmount")
    @Mapping(target = "additionalParameters", source = "app.additionalParameters")
    @Mapping(target = "userId", source = "app.userId")
    @Mapping(target = "income", source = "app.income")
    @Mapping(target = "createdAt", source = "app.createdAt")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdBy", source = "app.createdBy")
    @Mapping(target = "updatedBy", source = "app.updatedBy")
    @Mapping(target = "version", source = "app.version")
    @Mapping(target = "requestAmount", source = "app.requestAmount")
    @Mapping(target = "loanTime", source = "app.loanTime")
    @Mapping(target = "paymentDate", source = "app.paymentDate")
    @Mapping(target = "step", source = "app.step")
    @Mapping(target = "cardIds", source = "app.cardIds")
    @Mapping(target = "loanPurpose", source = "app.loanPurpose")
    @Mapping(target = "contactIds", source = "contactIds")
    @Mapping(target = "livingRegion", source = "request.region")
    @Mapping(target = "livingDistrict", source = "request.district")
    @Mapping(target = "livingAddress", source = "request.address")
    @Mapping(target = "livingHomeNumber", source = "request.homeNumber")
    CreditApplication toEntity(CreditApplication app, ScoringRequest request, List<UUID> contactIds);

    /**
     * Maps loan contacts and the old credit application to the new credit application entity.
     *
     * @param app Loan application entity.
     * @param contactIds List of contact IDs.
     * @return Credit application entity.
     */
    @Mapping(target = "contactIds", source = "contactIds")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    CreditApplication toEntity(CreditApplication app, List<UUID> contactIds);
}
