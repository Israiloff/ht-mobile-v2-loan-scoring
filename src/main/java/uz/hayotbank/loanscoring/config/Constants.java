package uz.hayotbank.loanscoring.config;

/**
 * The Constants class serves as a container for application-wide constant values.
 * It is designed to provide static final fields that remain consistent throughout the lifecycle
 * of the application. This class cannot be instantiated.
 */
public final class Constants {

    private Constants() {
    }

    /**
     * A constant representing the key or identifier for the "card-close" actor.
     * This value is used to denote actions, configurations, or events associated
     * with card closure processes in the application.
     */
    public static final String ACTOR = "loanScoring";
}
