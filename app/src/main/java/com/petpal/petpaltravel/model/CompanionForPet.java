package com.petpal.petpaltravel.model;

import java.util.GregorianCalendar;
//Demand
public class CompanionForPet {
    //Attributes
    private int id;
    private int ideUserShelterOffering;
    private String nameShelter;
    private String namePet;
    private GregorianCalendar availableFrom;
    private GregorianCalendar deadline;
    private String originCity;
    private String destinyCity;
    private String typePet;
    private String comments;
    private int idUserPersonSeleted;
    private String namePSelected;
    private int idUserPersonInterested1;
    private String namePInterested1;
    private int idUserPersonInterested2;
    private String namePInterested2;
    private int idUserPersonInterested3;
    private String namePInterested3;

    public CompanionForPet(int id, int ideUserShelterOffering, String nameShelter, String namePet,
                           GregorianCalendar availableFrom, GregorianCalendar deadline, String originCity,
                           String destinyCity, String typePet, String comments, int idUserPersonSeleted,
                           String namePSelected, int idUserPersonInterested1, String namePInterested1,
                           int idUserPersonInterested2, String namePInterested2,
                           int idUserPersonInterested3, String namePInterested3) {
        this.id = id;
        this.ideUserShelterOffering = ideUserShelterOffering;
        this.nameShelter = nameShelter;
        this.namePet = namePet;
        this.availableFrom = availableFrom;
        this.deadline = deadline;
        this.originCity = originCity;
        this.destinyCity = destinyCity;
        this.typePet = typePet;
        this.comments = comments;
        this.idUserPersonSeleted = idUserPersonSeleted;
        this.namePSelected = namePSelected;
        this.idUserPersonInterested1 = idUserPersonInterested1;
        this.namePInterested1 = namePInterested1;
        this.idUserPersonInterested2 = idUserPersonInterested2;
        this.namePInterested2 = namePInterested2;
        this.idUserPersonInterested3 = idUserPersonInterested3;
        this.namePInterested3 = namePInterested3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdeUserShelterOffering() {
        return ideUserShelterOffering;
    }

    public void setIdeUserShelterOffering(int ideUserShelterOffering) {
        this.ideUserShelterOffering = ideUserShelterOffering;
    }

    public String getNameShelter() {
        return nameShelter;
    }

    public void setNameShelter(String nameShelter) {
        this.nameShelter = nameShelter;
    }

    public String getNamePet() {
        return namePet;
    }

    public void setNamePet(String namePet) {
        this.namePet = namePet;
    }

    public GregorianCalendar getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(GregorianCalendar availableFrom) {
        this.availableFrom = availableFrom;
    }

    public GregorianCalendar getDeadline() {
        return deadline;
    }

    public void setDeadline(GregorianCalendar deadline) {
        this.deadline = deadline;
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public String getDestinyCity() {
        return destinyCity;
    }

    public void setDestinyCity(String destinyCity) {
        this.destinyCity = destinyCity;
    }

    public String getTypePet() {
        return typePet;
    }

    public void setTypePet(String typePet) {
        this.typePet = typePet;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getIdUserPersonSeleted() {
        return idUserPersonSeleted;
    }

    public void setIdUserPersonSeleted(int idUserPersonSeleted) {
        this.idUserPersonSeleted = idUserPersonSeleted;
    }

    public String getNamePSelected() {
        return namePSelected;
    }

    public void setNamePSelected(String namePSelected) {
        this.namePSelected = namePSelected;
    }

    public int getIdUserPersonInterested1() {
        return idUserPersonInterested1;
    }

    public void setIdUserPersonInterested1(int idUserPersonInterested1) {
        this.idUserPersonInterested1 = idUserPersonInterested1;
    }

    public String getNamePInterested1() {
        return namePInterested1;
    }

    public void setNamePInterested1(String namePInterested1) {
        this.namePInterested1 = namePInterested1;
    }

    public int getIdUserPersonInterested2() {
        return idUserPersonInterested2;
    }

    public void setIdUserPersonInterested2(int idUserPersonInterested2) {
        this.idUserPersonInterested2 = idUserPersonInterested2;
    }

    public String getNamePInterested2() {
        return namePInterested2;
    }

    public void setNamePInterested2(String namePInterested2) {
        this.namePInterested2 = namePInterested2;
    }

    public int getIdUserPersonInterested3() {
        return idUserPersonInterested3;
    }

    public void setIdUserPersonInterested3(int idUserPersonInterested3) {
        this.idUserPersonInterested3 = idUserPersonInterested3;
    }

    public String getNamePInterested3() {
        return namePInterested3;
    }

    public void setNamePInterested3(String namePInterested3) {
        this.namePInterested3 = namePInterested3;
    }
}
