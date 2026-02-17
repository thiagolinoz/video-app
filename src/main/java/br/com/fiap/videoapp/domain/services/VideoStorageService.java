package br.com.fiap.videoapp.domain.services;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.fiap.videoapp.domain.enums.VideoStatusEnum;
import br.com.fiap.videoapp.domain.models.PersonModel;
import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.domain.models.events.VideoUploadedModel;
import br.com.fiap.videoapp.domain.ports.in.VideoStorageServicePort;
import br.com.fiap.videoapp.domain.ports.out.FileEventPublisherPort;
import br.com.fiap.videoapp.domain.ports.out.PersonRepositoryPort;
import br.com.fiap.videoapp.domain.ports.out.VideoMetadaRepositoryPort;
import br.com.fiap.videoapp.domain.ports.out.VideoStorageRepositoryPort;
import br.com.fiap.videoapp.infraestructure.commons.mappers.VideoMapper;
import br.com.fiap.videoapp.infraestructure.commons.mappers.VideoUploadedMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VideoStorageService implements VideoStorageServicePort {

    private static final String uploadVideoPath = "videos";
    private final VideoMetadaRepositoryPort videoMetadaRepositoryPort;
    private final VideoStorageRepositoryPort videoStorageRepositoryPort;
    private final FileEventPublisherPort fileEventPublisherPort;
    private final PersonRepositoryPort personRepositoryPort;
    private static final Logger logger = Logger.getLogger(VideoStorageService.class.getName());

    public VideoStorageService(VideoMetadaRepositoryPort videoMetadaRepositoryPort, VideoStorageRepositoryPort videoStorageRepositoryPort,
                               FileEventPublisherPort fileEventPublisherPort,
                               PersonRepositoryPort personRepositoryPort) {
        this.videoMetadaRepositoryPort = videoMetadaRepositoryPort;
        this.videoStorageRepositoryPort = videoStorageRepositoryPort;
        this.fileEventPublisherPort = fileEventPublisherPort;
        this.personRepositoryPort = personRepositoryPort;
    }

    @Override
    public void store(MultipartFile file, String email) {
        Optional<PersonModel> personByEmail = personRepositoryPort.getPersonByEmail(email);
        // TODO: Remover mock para integração com o dynamo
//        Optional<PersonModel> personByEmail = Optional.ofNullable(new PersonModel.Builder()
//                .setNmName("MOCK_DO_MYKIN")
//                .setNmEmail("mykemaster@hotmail.com")
//                .setCdPassword("Mykesenha")
//                .build());

        if (personByEmail.isEmpty()) throw new RuntimeException("This person does not exists");

        String idVideo = UUID.randomUUID().toString();
        String fileNameWhitUUID = idVideo + "_" + file.getOriginalFilename();
        VideoStatusEnum videoStatus = VideoStatusEnum.RECEIVED;


        try {
            // Upload to S3
            String savedFilePath = videoStorageRepositoryPort.store(file, fileNameWhitUUID, uploadVideoPath, personByEmail.get());

            VideoModel videoModel = new VideoModel().buildVideoModel(idVideo,
                    file.getOriginalFilename(),
                    savedFilePath,
                    videoStatus,
                    personByEmail.get());
            logger.log(Level.INFO, "video model: " + videoModel);

            // Save DynamoDb
            logger.log(Level.INFO, "start save metadata for new video");
            videoMetadaRepositoryPort.save(videoModel);

            // Publish Kafka
            logger.log(Level.INFO, "start publish message for new video");
            fileEventPublisherPort.publish(videoModel);

            // TODO: Implementar publish da fila de status
        } catch (RuntimeException e) {
            videoStatus = VideoStatusEnum.PROCESS_ERROR;
            logger.log(Level.SEVERE, "An error occurred to upload a file", e);
        }
    }
}
