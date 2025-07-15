package uz.hayotbank.camundarest;

import java.time.Duration;

/**
 * Configuration properties for WebClient settings.
 *
 * @param maxConnections         The maximum number of connections allowed.
 * @param pendingAcquireMaxCount The maximum number of acquire attempts allowed while waiting
 *                                for a connection to become available.
 * @param pendingAcquireTimeout  The maximum duration to wait for acquiring a connection.
 * @param maxIdleTime            The maximum time a connection can remain idle before being closed.
 * @param maxLifeTime            The maximum lifetime of a connection before it is evicted.
 * @param responseTimeout        The maximum duration to wait for a response.
 */
public record WebClientProperties(Integer maxConnections,
                                  Integer pendingAcquireMaxCount,
                                  Duration pendingAcquireTimeout,
                                  Duration maxIdleTime,
                                  Duration maxLifeTime,
                                  Duration responseTimeout) {
}
