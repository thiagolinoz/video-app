package br.com.fiap.videoapp.domain.models;

public class PersonModel {

    private String nmEmail;
    private String nmName;
    private String cdPassword;

    public PersonModel() {
    }

    public PersonModel(String nmEmail, String nmName, String cdPassword) {
        this.nmEmail = nmEmail;
        this.nmName = nmName;
        this.cdPassword = cdPassword;
    }

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

    //TODO remover caso nao esteja utilizando
    public static  class Builder{
        private String nmEmail;
        private String nmName;
        private String cdPassword;

        public PersonModel.Builder setNmEmail(String nmEmail) {
            this.nmEmail = nmEmail;
            return this;
        }

        public PersonModel.Builder setNmName(String nmName) {
            this.nmName = nmName;
            return this;
        }

        public PersonModel.Builder setCdPassword(String cdPassword) {
            this.cdPassword = cdPassword;
            return this;
        }

        public PersonModel build() {
            return  new PersonModel(nmEmail, nmName, cdPassword);
        }
    }
}
