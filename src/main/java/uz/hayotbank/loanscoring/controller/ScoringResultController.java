package uz.hayotbank.loanscoring.controller;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import uz.cbssolutions.commons.loan.obtain.error.CreditApplicationNotBelongToUserException;
import uz.cbssolutions.commons.loan.obtain.error.CreditApplicationNotFoundException;
import uz.cbssolutions.commons.loan.obtain.models.LoanResult;
import uz.cbssolutions.commons.loan.obtain.utils.service.CreditAppService;
import uz.cbssolutions.commons.loan.obtain.utils.util.ResultUtil;
import uz.hayotbank.commons.security.core.model.UserDetails;
import uz.hayotbank.loanscoring.config.Constants;

/**
 * ScoringResultController is a REST controller responsible for handling requests
 * to retrieve the scoring result for a specific loan application.
 * It provides an endpoint to fetch the scoring result based on the application ID.
 */
@Slf4j
@RestController
@RequestMapping("/api/scoring/result")
@RequiredArgsConstructor
public class ScoringResultController {

    private final CreditAppService creditAppService;
    private final ResultUtil resultUtil;

    /**
     * Retrieves the scoring result for a specific loan application.
     *
     * @param locale the locale of the request, used for localization purposes.
     * @param applicationId the unique identifier of the loan application.
     * @param userDetails the user details object containing information about the user
     *        performing the request, including their unique identifier.
     * @return a {@link Mono} containing the {@link LoanResult} object, which includes common loan
     *         information.
     */
    @GetMapping
    public Mono<LoanResult> getScoringResult(
            @RequestHeader(Constants.HEADER_KEY_LOCALE) Locale locale,
            @RequestParam("application_id") UUID applicationId,
            UserDetails userDetails) {
        log.debug("getScoringResult started for applicationId: {}", applicationId);
        return creditAppService
                .findDetailedById(applicationId, Constants.ACTOR)
                .switchIfEmpty(Mono.error(
                        new CreditApplicationNotFoundException(applicationId, Constants.ACTOR)))
                .filter(app -> Objects.equals(app.getUserId(), userDetails.getId()))
                .switchIfEmpty(Mono.error(new CreditApplicationNotBelongToUserException(
                        applicationId, Constants.ACTOR)))
                .map(app -> resultUtil.toLoanResult(app, locale));
    }
}
