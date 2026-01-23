package br.com.fiap.videoapp.infraestructure.persistence.repositories;

import br.com.fiap.videoapp.domain.models.PersonModel;
import br.com.fiap.videoapp.domain.ports.out.PersonRepositoryPort;
import br.com.fiap.videoapp.infraestructure.commons.mappers.PersonMapper;
import br.com.fiap.videoapp.infraestructure.persistence.entities.PersonEntity;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.Optional;

@Component
public class PersonRepository implements PersonRepositoryPort {

    private final DynamoDbTable<PersonEntity> tablePerson;

    public PersonRepository(DynamoDbEnhancedClient enhancedClient) {
        TableSchema<PersonEntity> schema = TableSchema.fromBean(PersonEntity.class);
        this.tablePerson = enhancedClient.table("Persons", schema);
    }

    @Override
    public PersonModel createPerson(PersonModel person) {
        PersonEntity personEntity = PersonMapper.toEntity(person);
        tablePerson.putItem(personEntity);
        return PersonMapper.toModel(personEntity);
    }

    @Override
    public Optional<PersonModel> getPersonByEmail(String email) {
        PersonEntity personEntity = tablePerson.getItem(Key.builder().partitionValue(email).build());
        return Optional.ofNullable(personEntity).map(PersonMapper::toModel);
    }
}
