package uz.hayotbank.camundarest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

/**
 * Configuration class for setting up and customizing the Camunda REST API integration.
 * This class is marked with the @Configuration annotation, indicating
 * that it contains Spring bean definitions and application context configuration.
 * <p>
 * It leverages the @Slf4j annotation to enable logging within the class.
 * <p>
 * This class can be used to define beans or configure aspects
 * necessary for connecting to or interacting with a Camunda REST API instance.
 */
@Configuration
@Slf4j
@EnableConfigurationProperties(CamundaRestProperties.class)
public class CamundaRestConfig {

    public static final String WEB_CLIENT = "camunda-rest-web-client";

    /**
     * @param properties of camunda rest service
     * @return webclient for camunda rest service
     */
    @Bean(WEB_CLIENT)
    public WebClient webClient(CamundaRestProperties properties) {

        log.info("{} properties  : {}", WEB_CLIENT, properties);

        ConnectionProvider connectionProvider = ConnectionProvider
                .builder(WEB_CLIENT.concat("-connection-provider"))
                .maxConnections(properties.webClientProperties().maxConnections())
                .pendingAcquireMaxCount(properties.webClientProperties().pendingAcquireMaxCount())
                .pendingAcquireTimeout(properties.webClientProperties().pendingAcquireTimeout())
                .maxIdleTime(properties.webClientProperties().maxIdleTime())
                .maxLifeTime(properties.webClientProperties().maxLifeTime())
                .build();

        HttpClient httpClient = HttpClient.create(connectionProvider)
                .responseTimeout(properties.webClientProperties().responseTimeout());

        ReactorClientHttpConnector reactorClientHttpConnector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder()
                .baseUrl(properties.baseUrl())
                .clientConnector(reactorClientHttpConnector)
                .build();
    }
}
