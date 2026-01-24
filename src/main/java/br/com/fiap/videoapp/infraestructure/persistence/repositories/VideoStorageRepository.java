package br.com.fiap.videoapp.infraestructure.persistence.repositories;

import br.com.fiap.videoapp.domain.enums.VideoStatusEnum;
import br.com.fiap.videoapp.domain.models.events.VideoUploadedModel;
import br.com.fiap.videoapp.domain.ports.out.VideoStorageRepositoryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.UploadRequest;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.Executors;

@Component
public class VideoStorageRepository implements VideoStorageRepositoryPort {

    private final S3TransferManager transferManager;
    private final String bucketName;

    public VideoStorageRepository(S3TransferManager transferManager, @Value("${aws.s3.bucket}") String bucketName) {
        this.transferManager = transferManager;
        this.bucketName = bucketName;
    }

    @Override
    public VideoUploadedModel store(MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        try {
            UploadRequest uploadRequest = UploadRequest.builder()
                    .putObjectRequest( b -> b.bucket(bucketName)
                            .key(fileName)
                            .contentType(file.getContentType()))
                    .requestBody(AsyncRequestBody.fromInputStream(
                            file.getInputStream(),
                            file.getSize(),
                            Executors.newVirtualThreadPerTaskExecutor()
                    ))
                    .build();

            transferManager.upload(uploadRequest)
                    .completionFuture()
                    .join();

            return toVideoUploadedModel(fileName);
        } catch (IOException e) {
            throw new RuntimeException("Error uploading video to bucket", e);
        }
    }

    private VideoUploadedModel toVideoUploadedModel(String fileNameStoraged) {
        return new VideoUploadedModel(
                "",
                "",
                VideoStatusEnum.RECEIVED.name(),
                fileNameStoraged,
                "paths3/" + fileNameStoraged,
                null,
                Instant.now(),
                null,
                ""
        );
    }
}
