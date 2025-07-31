package uz.hayotbank.camundarest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * camunda rest properties.
 *
 * @param baseUrl             base url
 * @param loanScoringEndpoint loan scoring endpoint
 * @param webClientProperties client config
 */
@ConfigurationProperties(prefix = "services.camunda-rest")
public record CamundaRestProperties(
        String baseUrl,
        String loanScoringEndpoint,
        WebClientProperties webClientProperties
) {
}
