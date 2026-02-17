package br.com.fiap.videoapp.infraestructure.persistence.repositories;

import java.io.IOException;
import java.text.Normalizer;
import java.util.concurrent.Executors;

import br.com.fiap.videoapp.domain.models.PersonModel;
import br.com.fiap.videoapp.domain.ports.out.VideoStorageRepositoryPort;
import br.com.fiap.videoapp.infraestructure.persistence.repositories.aws.utils.S3ObjectKeyBuilder;
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
    private final S3ObjectKeyBuilder s3Utils;

    public VideoStorageRepository(S3TransferManager transferManager, @Value("${aws.s3.bucket}") String bucketName, S3ObjectKeyBuilder s3Utils) {
        this.transferManager = transferManager;
        this.bucketName = bucketName;
        this.s3Utils = s3Utils;
    }

    @Override
    public String store(MultipartFile file, String fileName, String path, PersonModel person) {
        String pathValue = s3Utils.buildVideoKey(path, person.getNmEmail(), fileName);
        try {
            UploadRequest uploadRequest = UploadRequest.builder()
                    .putObjectRequest( b -> b.bucket(bucketName)
                            .key(pathValue)
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
            return pathValue;
        } catch (IOException e) {
            throw new RuntimeException("Error uploading video to bucket", e);
        }
    }
}
