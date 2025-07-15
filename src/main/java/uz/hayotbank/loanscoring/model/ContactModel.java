package uz.hayotbank.loanscoring.model;

import java.io.Serializable;

/**
 * Contact details.
 *
 * @param name  Contact name.
 * @param phone Contact phone number.
 * @param type  Contact type.
 */
public record ContactModel(
        String name,
        String phone,
        String type) implements Serializable {
}
