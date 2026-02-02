package br.com.fiap.videoapp.infraestructure.event.publisher;

import br.com.fiap.videoapp.domain.models.events.VideoUploadedModel;
import br.com.fiap.videoapp.domain.ports.out.FileEventPublisherPort;
import br.com.fiap.videoapp.infraestructure.commons.mappers.CloudEventMapper;
import org.springframework.stereotype.Component;

@Component
public class FileEventPublisher implements FileEventPublisherPort {

    @Override
    public void publish(VideoUploadedModel videoModel) {
        CloudEventMapper.toCloudEvent(videoModel);

        //TODO publica no topico kafka
    }
}
