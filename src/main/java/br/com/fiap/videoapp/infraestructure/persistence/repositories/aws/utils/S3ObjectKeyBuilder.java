package br.com.fiap.videoapp.infraestructure.persistence.repositories.aws.utils;

import org.springframework.stereotype.Service;

import java.text.Normalizer;

@Service
public class S3ObjectKeyBuilder {

    public String buildVideoKey(
            String path,
            String user,
            String originalFileName
    ) {

        String normalized = normalizeFileName(originalFileName);

        return String.format(
               "%s/%s/%s",
                path,
                user,
                normalized
        );
    }

    private static String normalizeFileName(String fileName) {

        String normalized = Normalizer
                .normalize(fileName, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");

        normalized = normalized
                .toLowerCase()
                .replaceAll("\\s+", "-")
                .replaceAll("[^a-z0-9\\.-]", "");

        return normalized;
    }
}
