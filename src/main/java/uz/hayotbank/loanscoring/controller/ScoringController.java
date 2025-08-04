package uz.hayotbank.loanscoring.controller;

import java.util.Locale;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import uz.cbssolutions.commons.loan.obtain.models.LoanResult;
import uz.cbssolutions.commons.loan.obtain.models.scoring.ScoringRequest;
import uz.cbssolutions.commons.loan.obtain.utils.service.CreditAppService;
import uz.cbssolutions.commons.loan.obtain.utils.util.ResultUtil;
import uz.hayotbank.commons.security.core.model.UserDetails;
import uz.hayotbank.loanscoring.config.Constants;
import uz.hayotbank.loanscoring.service.ScoringService;

/**
 * ScoringController is a REST controller responsible for handling loan scoring requests.
 * It provides an endpoint for receiving loan scoring input data and returning a
 * scoring response to the caller.
 * <p>
 * This controller handles operations related to processing loan scoring using
 * the provided scoring request details. It accepts parameters such as locale
 * information and user details to aid in request processing.
 */
@Slf4j
@RestController
@RequestMapping("/api/scoring")
@RequiredArgsConstructor
public class ScoringController {

    private final ScoringService scoringService;
    private final CreditAppService creditAppService;
    private final ResultUtil resultUtil;

    /**
     * Processes a loan scoring request and returns the scoring response.
     *
     * @param scoringRequest the request body containing loan scoring information such as
     *                       application details, region, address, contacts, and other
     *                       relevant parameters.
     * @param locale         the locale of the request, obtained from the request header, used
     *                       for localization purposes in the scoring process.
     * @param userDetails    the user details object containing information about the user
     *                       performing the scoring action, including their unique identifier.
     * @return a {@link Mono} containing the {@link LoanResult} object, which includes common loan information.
     */
    @PostMapping
    public Mono<LoanResult> scoring(
            @RequestBody ScoringRequest scoringRequest,
            @RequestHeader(Constants.HEADER_KEY_LOCALE) Locale locale,
            UserDetails userDetails) {
        log.debug("scoring started for request: {}", scoringRequest);
        log.debug("user details: {}", userDetails);
        return scoringService.score(scoringRequest, locale)
                .then(creditAppService.findDetailedById(scoringRequest.applicationId(),
                        Constants.ACTOR))
                .map(app -> resultUtil.toLoanResult(app, locale));
    }
}
