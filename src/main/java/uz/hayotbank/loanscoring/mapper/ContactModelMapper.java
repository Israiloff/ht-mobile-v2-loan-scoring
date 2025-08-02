package uz.hayotbank.loanscoring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.cbssolutions.commons.loan.obtain.models.scoring.ContactModel;
import uz.cbssolutions.loan.enities.loan.Contact;

/**
 * Contact entity mapper.
 */
@Mapper(componentModel = "spring")
public interface ContactModelMapper {

    /**
     * Maps contact model to contact entity.
     *
     * @param contact Contact model.
     * @return Contact entity.
     */
    @Mapping(target = "nearby", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "typeId", source = "type")
    @Mapping(target = "createdOn", expression = "java(java.time.LocalDateTime.now())")
    Contact toEntity(ContactModel contact);
}
