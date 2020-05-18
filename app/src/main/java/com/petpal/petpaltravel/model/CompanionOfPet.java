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
    private int idUserShelterInterested1;
    private String nameSInterested1;
    private int idUserShelterInterested2;
    private String nameSInterested2;
    private int idUserShelterInterested3;
    private String nameSInterested3;

    public CompanionOfPet(int id, int ideUserPersonOffering, String namePerson, Calendar dateTravel,
                          String originCity, String destinyCity, String transport, String petType,
                          String comments, int idUserShelterSeleted, String nameSSelected,
                          int idUserShelterInterested1, String nameSInterested1, int idUserShelterInterested2,
                          String nameSInterested2, int idUserShelterInterested3, String nameSInterested3) {
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
        this.idUserShelterInterested1 = idUserShelterInterested1;
        this.nameSInterested1 = nameSInterested1;
        this.idUserShelterInterested2 = idUserShelterInterested2;
        this.nameSInterested2 = nameSInterested2;
        this.idUserShelterInterested3 = idUserShelterInterested3;
        this.nameSInterested3 = nameSInterested3;
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

    public void setDateTravel(GregorianCalendar dateTravel) {
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

    public int getIdUserShelterSeleted() {
        return idUserShelterSeleted;
    }

    public void setIdUserShelterSeleted(int idUserShelterSeleted) {
        this.idUserShelterSeleted = idUserShelterSeleted;
    }

    public String getNameSSelected() {
        return nameSSelected;
    }

    public void setNameSSelected(String nameSSelected) {
        this.nameSSelected = nameSSelected;
    }

    public int getIdUserShelterInterested1() {
        return idUserShelterInterested1;
    }

    public void setIdUserShelterInterested1(int idUserShelterInterested1) {
        this.idUserShelterInterested1 = idUserShelterInterested1;
    }

    public String getNameSInterested1() {
        return nameSInterested1;
    }

    public void setNameSInterested1(String nameSInterested1) {
        this.nameSInterested1 = nameSInterested1;
    }

    public int getIdUserShelterInterested2() {
        return idUserShelterInterested2;
    }

    public void setIdUserShelterInterested2(int idUserShelterInterested2) {
        this.idUserShelterInterested2 = idUserShelterInterested2;
    }

    public String getNameSInterested2() {
        return nameSInterested2;
    }

    public void setNameSInterested2(String nameSInterested2) {
        this.nameSInterested2 = nameSInterested2;
    }

    public int getIdUserShelterInterested3() {
        return idUserShelterInterested3;
    }

    public void setIdUserShelterInterested3(int idUserShelterInterested3) {
        this.idUserShelterInterested3 = idUserShelterInterested3;
    }

    public String getNameSInterested3() {
        return nameSInterested3;
    }

    public void setNameSInterested3(String nameSInterested3) {
        this.nameSInterested3 = nameSInterested3;
    }
}
