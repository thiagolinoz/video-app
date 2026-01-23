package br.com.fiap.videoapp.infraestructure.persistence.repositories.dynamo.config;

import br.com.fiap.videoapp.infraestructure.persistence.entities.PersonEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Configuration
public class DynamoDbInitializer {

    @Value("${aws.dynamodb.create-tables:false}")
    private boolean shouldCreate;

    private final DynamoDbEnhancedClient enhancedClient;

    public DynamoDbInitializer(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setupTables() {
        if (!shouldCreate) return;
        DynamoDbTable<PersonEntity> table = enhancedClient.table("Persons", TableSchema.fromBean(PersonEntity.class));

        try {
            table.createTable();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
