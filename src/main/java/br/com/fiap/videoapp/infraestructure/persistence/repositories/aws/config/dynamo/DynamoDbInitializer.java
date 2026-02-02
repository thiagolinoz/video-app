package br.com.fiap.videoapp.infraestructure.persistence.repositories.aws.config.dynamo;

import br.com.fiap.videoapp.infraestructure.persistence.entities.PersonEntity;
import br.com.fiap.videoapp.infraestructure.persistence.entities.VideoEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.ResourceInUseException;

@Configuration
public class DynamoDbInitializer {

    @Value("${aws.dynamodb.create-tables:false}")
    private boolean shouldCreate;

    private final DynamoDbEnhancedClient enhancedClient;

    public DynamoDbInitializer(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        if (!shouldCreate) return;
        setupTables(PersonEntity.class, "Persons");
        setupTables(VideoEntity.class, "Videos");
    }

    public void setupTables(Class<?> clazz, String tableName) {
        try {
            DynamoDbTable<?> table = enhancedClient.table(tableName, TableSchema.fromBean(clazz));
            table.createTable();
        }catch (ResourceInUseException re) {
            System.out.println("Table already exists");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
