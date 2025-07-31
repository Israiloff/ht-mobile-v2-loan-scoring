package uz.hayotbank.loanscoring.service.impl;

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
import uz.hayotbank.loanscoring.service.ContactService;

import java.util.List;
import java.util.UUID;

/**
 * {@inheritDoc}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    private final ContactModelMapper contactMapper;


    /**
     * {@inheritDoc}.
     */
    @Override
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
