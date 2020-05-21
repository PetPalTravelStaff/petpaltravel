package com.petpal.petpaltravel.model;

import java.util.Calendar;
import java.util.GregorianCalendar;
//Offer
public class CompanionOfPet {
    //Attributes
    private int id;
    private int ideUserPersonOffering;
    private String namePerson;
    private Calendar dateTravel;
    private String originCity;
    private String destinyCity;
    private String transport;
    private String petType;
    private String comments;
    private int idUserShelterSeleted;
    private String nameSSelected;
    private int[] idShelterInterested={0,0,0};
    private String[] namesShelterInterested={null,null,null};

    public CompanionOfPet(int id, int ideUserPersonOffering, String namePerson, Calendar dateTravel,
                          String originCity, String destinyCity, String transport, String petType,
                          String comments, int idUserShelterSeleted, String nameSSelected) {
        this.id = id;
        this.ideUserPersonOffering = ideUserPersonOffering;
        this.namePerson = namePerson;
        this.dateTravel = dateTravel;
        this.originCity = originCity;
        this.destinyCity = destinyCity;
        this.transport = transport;
        this.petType = petType;
        this.comments = comments;
        this.idUserShelterSeleted = idUserShelterSeleted;
        this.nameSSelected = nameSSelected;

    }

    public CompanionOfPet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdeUserPersonOffering() {
        return ideUserPersonOffering;
    }

    public void setIdeUserPersonOffering(int ideUserPersonOffering) {
        this.ideUserPersonOffering = ideUserPersonOffering;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson;
    }

    public Calendar getDateTravel() {
        return dateTravel;
    }

    public void setDateTravel(Calendar dateTravel) {
        this.dateTravel = dateTravel;
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

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public void setIdShelterIntPosition(int idShelterInterested, int position) {
        this.idShelterInterested[position] = idShelterInterested;
    }

    public void setNamesShelterIntPosition(String nameShelterInterested, int position) {
        this.namesShelterInterested[position] = nameShelterInterested;
    }

    public int getIdShelterIntPosition(int position) {
        return idShelterInterested[position];
    }

    public String getNamesShelterIntPosition(int position) {
        return namesShelterInterested[position];
    }
}
