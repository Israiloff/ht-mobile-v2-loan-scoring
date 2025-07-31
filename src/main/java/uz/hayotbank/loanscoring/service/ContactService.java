package uz.hayotbank.loanscoring.service;

import reactor.core.publisher.Flux;
import uz.cbssolutions.commons.loan.obtain.models.scoring.ContactModel;
import uz.cbssolutions.loan.enities.loan.Contact;

import java.util.List;
import java.util.UUID;

/**
 * Contact service default implementation.
 */

public interface ContactService {


    /**
     * Saves a list of contact models associated with a specific application ID into the repository.
     * The method transforms the contact models into contact entities and persists them.
     * Handles empty results and errors by throwing appropriate exceptions.
     *
     * @param contacts      the list of contact models to save
     * @param applicationId the UUID of the application associated with the contacts
     * @return a Flux containing the saved contact entities
     */
    Flux<Contact> saveAll(List<ContactModel> contacts, UUID applicationId);
}
