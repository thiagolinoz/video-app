package br.com.fiap.videoapp.infraestructure.event.publisher;

import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.domain.models.events.VideoUploadedModel;
import br.com.fiap.videoapp.domain.ports.out.FileEventPublisherPort;
import br.com.fiap.videoapp.infraestructure.commons.mappers.CloudEventMapper;
import br.com.fiap.videoapp.infraestructure.commons.mappers.VideoUploadedMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class FileEventPublisher implements FileEventPublisherPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final VideoUploadedMapper mapper;

    public FileEventPublisher(KafkaTemplate<String, String> kafkaTemplate, VideoUploadedMapper mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }

    @Override
    public void publish(VideoModel videoModel) {
        //CloudEventMapper.toCloudEvent(videoModel);
        VideoUploadedModel videoUploadedModel = mapper.toVideoUploadedModel(videoModel);
        try {
            String payload = objectMapper.writeValueAsString(videoUploadedModel);
            kafkaTemplate.send("received-videos", payload);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
