package br.com.fiap.videoapp.infraestructure.persistence.repositories;

import java.io.IOException;
import java.util.concurrent.Executors;

import br.com.fiap.videoapp.domain.ports.out.VideoStorageRepositoryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.UploadRequest;

@Component
public class VideoStorageRepository implements VideoStorageRepositoryPort {

    private final S3TransferManager transferManager;
    private final String bucketName;

    public VideoStorageRepository(S3TransferManager transferManager, @Value("${aws.s3.bucket}") String bucketName) {
        this.transferManager = transferManager;
        this.bucketName = bucketName;
    }

    @Override
    public void store(MultipartFile file, String fileName) {
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
        } catch (IOException e) {
            throw new RuntimeException("Error uploading video to bucket", e);
        }
    }
}
