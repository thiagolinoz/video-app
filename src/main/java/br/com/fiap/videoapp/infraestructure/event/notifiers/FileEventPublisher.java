package br.com.fiap.videoapp.infraestructure.event.notifiers;

import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.infraestructure.commons.mappers.VideoUpdateStatusMessageMapper;
import br.com.fiap.videoapp.infraestructure.event.messages.VideoUpdateStatusMessage;
import br.com.fiap.videoapp.infraestructure.event.messages.VideoUploadedMessage;
import br.com.fiap.videoapp.domain.ports.out.FileEventPublisherPort;
import br.com.fiap.videoapp.infraestructure.commons.mappers.VideoUploadedMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class FileEventPublisher implements FileEventPublisherPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(FileEventPublisher.class.getName());

    public FileEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishNewVideo(VideoModel videoModel) {
        //CloudEventMapper.toCloudEvent(videoModel);
        VideoUploadedMessage videoUploadedMessage = VideoUploadedMapper.toVideoUploadedModel(videoModel);
        try {
            String payload = objectMapper.writeValueAsString(videoUploadedMessage);
            logger.log(Level.INFO, "Publishing message on topic received-videos= ", videoUploadedMessage.toString());
            kafkaTemplate.send("received-videos", payload);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void publishStatusVideo(VideoModel videoModel) {
        VideoUpdateStatusMessage videoUpdateStatusMessage = VideoUpdateStatusMessageMapper.toMessage(videoModel);
        try {
            String payload = objectMapper.writeValueAsString(videoUpdateStatusMessage);
            logger.log(Level.INFO, "Publishing message on topic process-status-videos = ", videoUpdateStatusMessage.toString());
            kafkaTemplate.send("process-status-videos", payload);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
