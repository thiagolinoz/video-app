package br.com.fiap.videoapp.infraestructure.persistence.repositories;

import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.domain.ports.out.VideoMetadaRepositoryPort;
import br.com.fiap.videoapp.domain.services.VideoStorageService;
import br.com.fiap.videoapp.infraestructure.commons.mappers.PersonMapper;
import br.com.fiap.videoapp.infraestructure.commons.mappers.VideoMapper;
import br.com.fiap.videoapp.infraestructure.commons.mappers.VideoUploadedMapper;
import br.com.fiap.videoapp.infraestructure.persistence.entities.PersonEntity;
import br.com.fiap.videoapp.infraestructure.persistence.entities.VideoEntity;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class VideoMetadaRepository implements VideoMetadaRepositoryPort {

    private final DynamoDbTable<VideoEntity> tableVideo;
    private static final Logger logger = Logger.getLogger(VideoMetadaRepository.class.getName());

    public VideoMetadaRepository(DynamoDbEnhancedClient enhancedClient) {
        TableSchema<VideoEntity> schema = TableSchema.fromBean(VideoEntity.class);
        this.tableVideo = enhancedClient.table("Videos", schema);
    }

    @Override
    public List<VideoModel> listVideos(String email) {
        QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder().partitionValue(email).build());

        List<VideoEntity> videoEntities = tableVideo.query(r -> r.queryConditional(queryConditional)
                        .scanIndexForward(false))
                .items()
                .stream()
                .toList();

        return videoEntities.stream().map(VideoMapper::toModel).toList();
    }


    @Override
    public VideoModel save(VideoModel video) {
        VideoEntity videoEntity = VideoMapper.toEntity(video);
        logger.log(Level.INFO, "Saving VideoEntity = ", videoEntity.toString());
        tableVideo.putItem(videoEntity);
        return VideoMapper.toModel(videoEntity);
    }

    @Override
    public Optional<VideoModel> findBy(String email, String idVideo) {
        QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(email)
                .sortValue(idVideo)
                .build());

        PageIterable<VideoEntity> results = tableVideo.query(r -> r.queryConditional(queryConditional));


        return results.stream()
                .flatMap(page -> page.items().stream())
                .findFirst()
                .map(VideoMapper::toModel);
    }
}
