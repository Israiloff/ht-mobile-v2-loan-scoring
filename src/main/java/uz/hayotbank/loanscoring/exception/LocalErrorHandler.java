package uz.hayotbank.loanscoring.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;
import uz.cbssolutions.commons.loan.obtain.error.LoanException;
import uz.cbssolutions.error.decoder.model.DecodeResult;
import uz.hayotbank.commons.error.handlers.service.ErrorDecoderClient;
import uz.hayotbank.loanscoring.config.Constants;

import java.util.Locale;

/**
 * Global exception handler for the application.
 * <p>
 * This class handles exceptions across the entire application at a centralized location
 * using `@RestControllerAdvice`. It ensures that errors are processed consistently
 * and meaningful responses are provided to the client when exceptions occur.
 * <p>
 * Typical responsibilities include:
 * - Handling custom application-defined exceptions.
 * - Managing generic runtime exceptions.
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class LocalErrorHandler {


    private final ErrorDecoderClient errorDecoderClient;

    /**
     * Handles `LoanException` exceptions by decoding error details and providing
     * a meaningful response.
     *
     * This method is triggered when a `LoanException` is thrown in the application.
     * It utilizes the `ErrorDecoderClient` to decode error information associated
     * with the exception, including actor, error code, and locale, to create a
     * `DecodeResult`.
     *
     * @param e      the exception containing details of the loan-related error
     * @param locale the locale information retrieved from the request header for
     *               determining locale-specific error messages
     * @return a `Mono` containing the decoded error result
     */
    @ExceptionHandler(LoanException.class)
    public Mono<DecodeResult> handle(LoanException e,
                                     @RequestHeader(Constants.HEADER_KEY_LOCALE) Locale locale) {
        return errorDecoderClient.decode(
                Constants.ACTOR,
                e.getCode(),
                locale
        );
    }
}
