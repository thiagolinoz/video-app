package br.com.fiap.videoapp.infraestructure.persistence.repositories;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.concurrent.Executors;

import br.com.fiap.videoapp.domain.models.PersonModel;
import br.com.fiap.videoapp.domain.ports.out.VideoStorageRepositoryPort;
import br.com.fiap.videoapp.infraestructure.persistence.repositories.aws.utils.S3ObjectKeyBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.DownloadFileRequest;
import software.amazon.awssdk.transfer.s3.model.DownloadRequest;
import software.amazon.awssdk.transfer.s3.model.UploadRequest;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.transfer.s3.model.DownloadRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Component
public class VideoStorageRepository implements VideoStorageRepositoryPort {

    private final S3TransferManager transferManager;
    private final S3Client s3Client;
    private final String bucketName;
    private final S3ObjectKeyBuilder s3Utils;

    public VideoStorageRepository(S3TransferManager transferManager, S3Client s3Client, @Value("${aws.s3.bucket}") String bucketName, S3ObjectKeyBuilder s3Utils) {
        this.transferManager = transferManager;
        this.s3Client = s3Client;
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
            return bucketName + "/" + pathValue;
        } catch (IOException e) {
            throw new RuntimeException("Error uploading video to bucket", e);
        }
    }

    @Override
    public InputStream download(String key) {
        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            s3Client.getObject(
                    GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .build(),
                    ResponseTransformer.toOutputStream(outputStream)
            );

            InputStream stream = new ByteArrayInputStream(outputStream.toByteArray());
            return stream;
        } catch (Exception e) {
            throw new RuntimeException("Error to download video from bucket", e);
        }

    }
}
