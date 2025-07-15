package uz.hayotbank.loanscoring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.cbssolutions.loan.enities.loan.CreditApplication;
import uz.hayotbank.loanscoring.model.ScoringRequest;

import java.util.List;
import java.util.UUID;

/**
 * Credit application entity mapper.
 */
@Mapper(componentModel = "spring")
public interface CreditAppMapper {

    /**
     * Maps a {@link CreditApplication} entity and a {@link ScoringRequest} model to a new {@link CreditApplication}
     * entity,
     * applying field mappings between the source objects and the target entity.
     *
     * @param app     the {@link CreditApplication} entity containing existing application data to be mapped
     * @param request the {@link ScoringRequest} model supplying additional request-specific data
     * @param cardIds a list of {@link UUID} values representing the IDs of cards associated with the application
     * @return a new {@link CreditApplication} entity with fields populated from the provided application data,
     * request data, and card IDs
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
    @Mapping(target = "updatedAt", source = "app.updatedAt")
    @Mapping(target = "createdBy", source = "app.createdBy")
    @Mapping(target = "updatedBy", source = "app.updatedBy")
    @Mapping(target = "version", source = "app.version")
    @Mapping(target = "requestAmount", source = "app.amount")
    @Mapping(target = "loanTime", source = "app.loanTime")
    @Mapping(target = "paymentDate", source = "app.paymentDate")
    @Mapping(target = "step", source = "app.step")
    @Mapping(target = "cardIds", source = "request.cardIds")
    @Mapping(target = "contactIds", source = "contactIds")
    @Mapping(target = "livingRegion", source = "request.region")
    @Mapping(target = "livingDistrict", source = "request.district")
    @Mapping(target = "livingAddress", source = "request.address")
    @Mapping(target = "livingHomeNumber", source = "request.homeNumber")
    @Mapping(target = "loanPurpose", source = "request.purpose")
    CreditApplication toEntity(CreditApplication app, ScoringRequest request, List<UUID> cardIds);

}
