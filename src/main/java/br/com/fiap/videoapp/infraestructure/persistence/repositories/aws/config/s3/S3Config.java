package br.com.fiap.videoapp.infraestructure.persistence.repositories.aws.config.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3CrtAsyncClientBuilder;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

import java.net.URI;

@Configuration
public class S3Config {

    @Value("${aws.s3.endpoint}")
    private String endpoint;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.s3.local-stack:false}")
    private Boolean isLocal;

    @Bean
    public S3AsyncClient s3AsyncClient() {

        S3CrtAsyncClientBuilder builder = S3AsyncClient.crtBuilder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider());

        if (isLocal) {
            builder.endpointOverride(URI.create(endpoint))
                    .forcePathStyle(true);
        }

        return builder.build();
    }

    @Bean
    public S3TransferManager s3TransferManager(S3AsyncClient s3AsyncClient) {
        return S3TransferManager.builder()
                .s3Client(s3AsyncClient)
                .build();
    }

    private DefaultCredentialsProvider credentialsProvider() {
        return  DefaultCredentialsProvider.builder().build();
    }

}
