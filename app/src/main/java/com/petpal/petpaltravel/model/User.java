package com.petpal.petpaltravel.model;

import java.util.List;

public abstract class User {

    //Attributes
    private int id;
    private String name;
    private String email;
    private String password;
    private int phone;
    private boolean isShelter;
    private int ranking;
    private List<CompanionOfPet> listOfOfferings;
    private List<CompanionForPet> listOfDemands;

    //Constructors
    public User(int id, String name, String email, String password, int phone, boolean isShelter, int ranking) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.isShelter = isShelter;
        this.ranking = ranking;
    }

    public User() {

    }


    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public boolean isShelter() {
        return isShelter;
    }

    public void setShelter(boolean shelter) {
        isShelter = shelter;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }


    //methods
}
