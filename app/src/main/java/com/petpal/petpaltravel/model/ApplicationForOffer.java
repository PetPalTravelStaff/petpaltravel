package com.petpal.petpaltravel.model;

public class ApplicationForOffer {
    //Attributes
    //Attributes
    private int idOffer;
    private int idShelterApplying;
    private String nameShelter;
    private String typePet;
    private String namePet;
    private String comments;
    private String mail;
    private String phone;

    public ApplicationForOffer(int idOffer, int idShelterApplying, String nameShelter, String typePet, String namePet, String comments, String mail, String phone) {
        this.idOffer = idOffer;
        this.idShelterApplying = idShelterApplying;
        this.nameShelter = nameShelter;
        this.typePet = typePet;
        this.namePet = namePet;
        this.comments = comments;
        this.mail = mail;
        this.phone = phone;
    }

    public ApplicationForOffer() {
    }

    public int getIdOffer() {
        return idOffer;
    }

    public void setIdOffer(int idOffer) {
        this.idOffer = idOffer;
    }

    public int getIdShelterApplying() {
        return idShelterApplying;
    }

    public void setIdShelterApplying(int idShelterApplying) {
        this.idShelterApplying = idShelterApplying;
    }

    public String getNameShelter() {
        return nameShelter;
    }

    public void setNameShelter(String nameShelter) {
        this.nameShelter = nameShelter;
    }

    public String getTypePet() {
        return typePet;
    }

    public void setTypePet(String typePet) {
        this.typePet = typePet;
    }

    public String getNamePet() {
        return namePet;
    }

    public void setNamePet(String namePet) {
        this.namePet = namePet;
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
