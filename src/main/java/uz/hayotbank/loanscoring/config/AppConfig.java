package uz.hayotbank.loanscoring.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Application configuration.
 */
@Configuration
@EnableConfigurationProperties({ ApplicationProperties.class })
public class AppConfig {
}
