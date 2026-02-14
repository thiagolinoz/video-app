package br.com.fiap.videoapp.infraestructure.event.publisher;

import br.com.fiap.videoapp.domain.models.events.VideoUploadedModel;
import br.com.fiap.videoapp.domain.ports.out.FileEventPublisherPort;
import br.com.fiap.videoapp.infraestructure.commons.mappers.CloudEventMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class FileEventPublisher implements FileEventPublisherPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FileEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(VideoUploadedModel videoModel) {
        //CloudEventMapper.toCloudEvent(videoModel);
        try {
            String payload = objectMapper.writeValueAsString(videoModel);
            kafkaTemplate.send("received-videos", payload);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //TODO publica no topico kafka
    }
}
