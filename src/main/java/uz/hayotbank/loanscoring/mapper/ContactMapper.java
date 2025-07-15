package uz.hayotbank.loanscoring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.cbssolutions.loan.enities.loan.Contact;
import uz.hayotbank.loanscoring.model.ContactModel;

/**
 * Mapper interface for converting {@link ContactModel} instances to {@link Contact} entities.
 * Utilizes MapStruct for object mapping and transformation.
 *
 * This interface is configured with the Spring component model, making it eligible for dependency
 * injection and allowing it to function as a Spring-managed bean.
 */
@Mapper(componentModel = "spring")
public interface ContactMapper {

    /**
     * Maps a {@link ContactModel} instance to a {@link Contact} entity, applying necessary transformations
     * to populate the entity fields, such as generating a random UUID for the ID.
     *
     * @param contact the {@link ContactModel} object to be converted to a {@link Contact} entity
     * @return the {@link Contact} entity with fields mapped from the provided {@link ContactModel}
     */
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "typeId", source = "type")
    Contact toEntity(ContactModel contact);
}
