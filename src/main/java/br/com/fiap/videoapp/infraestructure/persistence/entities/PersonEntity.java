package br.com.fiap.videoapp.infraestructure.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class PersonEntity {

    private String nmEmail;
    private String nmName;
    private String cdPassword;

    @DynamoDbPartitionKey
    public String getNmEmail() {
        return nmEmail;
    }

    public void setNmEmail(String nmEmail) {
        this.nmEmail = nmEmail;
    }

    public String getNmName() {
        return nmName;
    }

    public void setNmName(String nmName) {
        this.nmName = nmName;
    }

    public String getCdPassword() {
        return cdPassword;
    }

    public void setCdPassword(String cdPassword) {
        this.cdPassword = cdPassword;
    }
}
