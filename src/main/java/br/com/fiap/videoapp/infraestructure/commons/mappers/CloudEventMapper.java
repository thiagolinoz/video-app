package br.com.fiap.videoapp.infraestructure.commons.mappers;

import br.com.fiap.videoapp.domain.models.events.VideoUploadedModel;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CloudEventMapper {

    public static Map<String, Object> toCloudEvent(VideoUploadedModel event) {
        Map<String, Object> envelope = new HashMap<>();
        envelope.put("specversion", "1.0");
        envelope.put("type", "com.video.app.upload");
        envelope.put("source", event.nmVideo());
        envelope.put("subject", "Video uploaded");
        envelope.put("id", UUID.randomUUID().toString());
        envelope.put("time", OffsetDateTime.now().toString());
        envelope.put("datacontenttype", "application/json");
        envelope.put("data", event);

        return envelope;
    }
}
