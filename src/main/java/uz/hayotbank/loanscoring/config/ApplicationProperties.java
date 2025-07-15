package uz.hayotbank.loanscoring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * Application properties.
 *
 * @param commonErrorCode   Common error code.
 * @param scoringStepNumber Scoring step number.
 */
@ConfigurationProperties(prefix = "application")
public record ApplicationProperties(String commonErrorCode, Integer scoringStepNumber) implements Serializable {
}
