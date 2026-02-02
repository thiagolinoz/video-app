package br.com.fiap.videoapp.infraestructure.commons.mappers;

import br.com.fiap.videoapp.domain.models.PersonModel;
import br.com.fiap.videoapp.infraestructure.persistence.entities.PersonEntity;

public class PersonMapper {

    public static PersonModel toModel(PersonEntity personEntity) {
        PersonModel personModel = new PersonModel();
        personModel.setNmEmail(personEntity.getNmEmail());
        personModel.setNmName(personEntity.getNmName());
        personModel.setCdPassword(personEntity.getCdPassword());
        return personModel;
    }

    public static PersonEntity toEntity(PersonModel personModel) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setNmEmail(personModel.getNmEmail());
        personEntity.setNmName(personModel.getNmName());
        personEntity.setCdPassword(personModel.getCdPassword());
        return personEntity;
    }
}
