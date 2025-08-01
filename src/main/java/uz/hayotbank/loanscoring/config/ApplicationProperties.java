package uz.hayotbank.loanscoring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * Application properties.
 *
 * @param scoringStepNumber Scoring step number.
 */
@ConfigurationProperties(prefix = "application")
public record ApplicationProperties(Integer scoringStepNumber) implements Serializable {
}
