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
    private int[] idPersonsInterested={0,0,0};
    private String[] namesPersonsInterested={null,null,null};

    public CompanionForPet(int id, int ideUserShelterOffering, String nameShelter, String namePet,
                           GregorianCalendar availableFrom, GregorianCalendar deadline, String originCity,
                           String destinyCity, String typePet, String comments, int idUserPersonSeleted,
                           String namePSelected) {
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
    }

    public CompanionForPet() {
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

    public int[] getIdPersonsInterested() {
        return idPersonsInterested;
    }

    public void setIdPersonsInterested(int[] idPersonsInterested) {
        this.idPersonsInterested = idPersonsInterested;
    }

    public String[] getNamesPersonsInterested() {
        return namesPersonsInterested;
    }

    public void setNamesPersonsInterested(String[] namesPersonsInterested) {
        this.namesPersonsInterested = namesPersonsInterested;
    }

    public int getIdPersonInterestePosition(int position) {
        return idPersonsInterested[position];
    }

    public String getNamePersonInterestedPosition(int position) {
        return namesPersonsInterested[position];
    }

    public void setNamePersonsIntPosition(String name, int position) {
        this.namesPersonsInterested[position]= name;
    }

    public void setIdPersonsIntPosition(int idPerson, int position) {
        this.idPersonsInterested[position] = idPerson;
    }

}
