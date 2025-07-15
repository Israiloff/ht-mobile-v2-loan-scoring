package uz.hayotbank.loanscoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The LoanScoringApplication class serves as the entry point for the Loan Scoring application.
 * It is annotated with {@code @SpringBootApplication} to enable component scanning,
 * configuration, and auto-configuration for the Spring Boot application.
 *
 * This application is designed for loan scoring operations and includes components
 * within the specified base package to be scanned by Spring.
 *
 * The main method uses the {@code SpringApplication.run} method to launch the application
 * context.
 */
@SpringBootApplication(
        scanBasePackages = {
                "uz"
        }
)
public class LoanScoringApplication {

    /**
     * The main method serves as the entry point for the Loan Scoring application.
     * It initializes the Spring Boot application context by invoking the {@code SpringApplication.run} method.
     *
     * @param args an array of command-line arguments passed to the application during startup
     */
    public static void main(String[] args) {
        SpringApplication.run(LoanScoringApplication.class, args);
    }

}
