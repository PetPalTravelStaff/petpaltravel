package com.petpal.petpaltravel.model;

public class ApplicationForDemand {
    //Attributes
    private int idApplForDem;
    private int idDemand;
    private int idPersonApplying;
    private String namePerson;
    private String transport;
    private String comments;
    private String mail;
    private String phone;

    public ApplicationForDemand(int idApplForDem, int idDemand, int idPersonApplying, String namePerson, String transport, String comments, String mail, String phone) {
        this.idApplForDem = idApplForDem;
        this.idDemand = idDemand;
        this.idPersonApplying = idPersonApplying;
        this.namePerson = namePerson;
        this.transport = transport;
        this.comments = comments;
        this.mail = mail;
        this.phone = phone;
    }

    public ApplicationForDemand() {
    }


    public int getIdApplForDem() {
        return idApplForDem;
    }

    public void setIdApplForDem(int idApplForDem) {
        this.idApplForDem = idApplForDem;
    }

    public int getIdDemand() {
        return idDemand;
    }

    public void setIdDemand(int idDemand) {
        this.idDemand = idDemand;
    }

    public int getIdPersonApplying() {
        return idPersonApplying;
    }

    public void setIdPersonApplying(int idPersonApplying) {
        this.idPersonApplying = idPersonApplying;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
