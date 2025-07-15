package uz.hayotbank.loanscoring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.cbssolutions.commons.loan.obtain.error.ContactSaveException;
import uz.cbssolutions.commons.loan.obtain.error.ContactSaveResultEmptyException;
import uz.cbssolutions.commons.loan.obtain.models.scoring.ContactModel;
import uz.cbssolutions.loan.enities.loan.Contact;
import uz.cbssolutions.loan.repository.ContactRepository;
import uz.hayotbank.loanscoring.config.Constants;
import uz.hayotbank.loanscoring.mapper.ContactModelMapper;

import java.util.List;
import java.util.UUID;

/**
 * Contact service default implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    private final ContactModelMapper contactMapper;

    /**
     * Saves a list of contact models associated with a specific application ID into the repository.
     * The method transforms the contact models into contact entities and persists them.
     * Handles empty results and errors by throwing appropriate exceptions.
     *
     * @param contacts      the list of contact models to save
     * @param applicationId the UUID of the application associated with the contacts
     * @return a Flux containing the saved contact entities
     */
    public Flux<Contact> saveAll(List<ContactModel> contacts, UUID applicationId) {
        log.debug("saveAll started for contacts: {}", contacts);
        return Flux
                .fromIterable(contacts)
                .map(contactMapper::toEntity)
                .flatMap(contactRepository::save)
                .switchIfEmpty(
                        Mono.error(new ContactSaveResultEmptyException(applicationId, contacts, Constants.ACTOR)))
                .onErrorMap(Throwable.class,
                        e -> new ContactSaveException(e, applicationId, contacts, Constants.ACTOR));
    }
}
