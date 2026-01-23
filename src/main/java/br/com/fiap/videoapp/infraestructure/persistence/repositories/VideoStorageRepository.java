package br.com.fiap.videoapp.infraestructure.persistence.repositories;

import br.com.fiap.videoapp.domain.ports.out.VideoStorageRepositoryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.UploadRequest;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Executors;

public class VideoStorageRepository implements VideoStorageRepositoryPort {

    private final S3TransferManager transferManager;

    @Value("${aws.s3.bucket}")
    private final String bucketName;

    public VideoStorageRepository(String bucketName, S3TransferManager transferManager) {
        this.transferManager = transferManager;
        this.bucketName = bucketName;
    }

    @Override
    public void store(MultipartFile file) {
        String fileName = file.getOriginalFilename() + "-" + UUID.randomUUID();
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
            //TODO retornar isto?
            transferManager.upload(uploadRequest)
                    .completionFuture()
                    .join()
                    .response()
                    .toString();
        } catch (IOException e) {
            throw new RuntimeException("Error uploading video to bucket", e);
        }
    }
}
