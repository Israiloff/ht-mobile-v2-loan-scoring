package uz.hayotbank.loanscoring.handler;

import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import uz.cbssolutions.commons.loan.obtain.error.LoanException;
import uz.cbssolutions.commons.loan.obtain.models.error.ErrorResult;
import uz.cbssolutions.commons.loan.obtain.utils.mapper.ErrorRequestMapper;
import uz.cbssolutions.commons.loan.obtain.utils.mapper.ErrorResultMapper;
import uz.cbssolutions.commons.loan.obtain.utils.service.ErrorDecoderClient;
import uz.hayotbank.commons.error.models.DecodableException;
import uz.hayotbank.loanscoring.config.Constants;

/**
 * Global exception handler for handling exceptions in a Spring application.
 * It uses the ErrorDecoderClient to decode errors and map them to a response.
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ScoringExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorDecoderClient errorDecoderClient;
    private final ErrorResultMapper errorResultMapper;
    private final ErrorRequestMapper errorRequestMapper;


    /**
     * Handles DecodableException by decoding the error using the ErrorDecoderClient
     * and mapping it to a response.
     *
     * @param ex The DecodableException to handle.
     * @param locale Current locale.
     * @return A Mono containing the mapped response.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DecodableException.class)
    public Mono<ErrorResult> handleDecodable(LoanException ex,
            @RequestHeader(Constants.HEADER_KEY_LOCALE) Locale locale) {
        log.error("Decodable error occurred", ex);
        return Mono
                .just(errorRequestMapper.map(ex.getActor(), ex.getCode(), ex.getApplicationId()))
                .flatMap(errorReq -> errorDecoderClient.decode(errorReq, locale))
                .map(errorResultMapper::map);
    }

    /**
     * Handles all other exceptions by logging the error and returning a generic error response.
     *
     * @param ex The exception to handle.
     * @param locale Current locale.
     * @return A Mono containing a generic error response.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Mono<ErrorResult> handleException(Exception ex,
            @RequestHeader(Constants.HEADER_KEY_LOCALE) Locale locale) {
        log.error("An unexpected error occurred", ex);
        return Mono
                .just(errorRequestMapper.map(Constants.ACTOR, Constants.UNKNOWN_ERROR_CODE))
                .flatMap(errorReq -> errorDecoderClient.decode(errorReq, locale))
                .map(errorResultMapper::map);
    }
}
