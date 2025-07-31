package uz.hayotbank.loanscoring.service.impl;

import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import uz.cbssolutions.commons.file.minio.configuration.BaseMinioConfiguration;
import uz.cbssolutions.commons.file.minio.service.MinioService;
import uz.cbssolutions.commons.loan.obtain.error.FaceIdSaveException;
import uz.cbssolutions.commons.loan.obtain.error.FaceIdSaveResultEmptyException;
import uz.cbssolutions.commons.loan.obtain.models.camunda.FaceId;
import uz.cbssolutions.commons.loan.obtain.utils.mapper.FaceIdMapper;
import uz.hayotbank.loanscoring.config.Constants;
import uz.hayotbank.loanscoring.service.FaceIdService;
import uz.hayotbank.loanscoring.util.FileUtils;

/**
 * {@inheritDoc}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FaceIdServiceImpl implements FaceIdService {

    private final MinioService minioService;

    private final FaceIdMapper faceIdMapper;

    private final BaseMinioConfiguration minioProperties;

    /**
     * {@inheritDoc}.
     */
    @Override
    public Mono<FaceId> save(String content, String userId, UUID applicationId) {
        log.debug("Saving faceId for user {}", userId);
        return minioService
                .addAttachment(FileUtils.convert(content),
                        userId + "/" + Instant.now().toEpochMilli() + ".jpg")
                .map(fileId -> faceIdMapper.toFaceId(fileId, minioProperties.getBucket()))
                .onErrorMap(throwable -> new FaceIdSaveException(applicationId, throwable,
                        Constants.ACTOR))
                .switchIfEmpty(Mono
                        .error(new FaceIdSaveResultEmptyException(applicationId, Constants.ACTOR)))
                .doOnNext(faceId -> log.debug("FaceId saved : {}", faceId));
    }

}
