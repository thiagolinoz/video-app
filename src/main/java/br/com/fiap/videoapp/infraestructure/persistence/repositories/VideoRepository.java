package br.com.fiap.videoapp.infraestructure.persistence.repositories;

import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.domain.ports.out.VideoRepositoryPort;
import br.com.fiap.videoapp.infraestructure.commons.mappers.VideoMapper;
import br.com.fiap.videoapp.infraestructure.persistence.entities.VideoEntity;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.List;

public class VideoRepository implements VideoRepositoryPort {

    private final DynamoDbTable<VideoEntity> tableVideo;

    public VideoRepository(DynamoDbEnhancedClient enhancedClient) {
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
}
