package com.petpal.petpaltravel.model;

import java.util.GregorianCalendar;

public class CompanionForPet {
    //Attributes
    private int id;
    private int ideUserShelterOffering;
    private String namePet;
    private GregorianCalendar availableFrom;
    private GregorianCalendar deadline;
    private int originCity;
    private int destinyCity;
    private String typePet;
    private String comments;
    private int idUserPersonSeleted;
    private int idUserPersonInterested1;
    private int idUserPersonInterested2;
    private int idUserPersonInterested3;

    public CompanionForPet(int id, int ideUserShelterOffering, String namePet, GregorianCalendar
            availableFrom, GregorianCalendar deadline, int originCity, int destinyCity, String typePet,
                           String comments, int idUserPersonSeleted, int idUserPersonInterested1,
                           int idUserPersonInterested2, int idUserPersonInterested3) {
        this.id = id;
        this.ideUserShelterOffering = ideUserShelterOffering;
        this.namePet = namePet;
        this.availableFrom = availableFrom;
        this.deadline = deadline;
        this.originCity = originCity;
        this.destinyCity = destinyCity;
        this.typePet = typePet;
        this.comments = comments;
        this.idUserPersonSeleted = idUserPersonSeleted;
        this.idUserPersonInterested1 = idUserPersonInterested1;
        this.idUserPersonInterested2 = idUserPersonInterested2;
        this.idUserPersonInterested3 = idUserPersonInterested3;
    }
}
