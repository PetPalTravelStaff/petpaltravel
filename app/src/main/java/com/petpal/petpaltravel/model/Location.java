package com.petpal.petpaltravel.model;

public class Location {
    private int id;
    private String CP;
    private String city;
    private String state;

    public Location(int id, String CP, String city, String state) {
        this.id = id;
        this.CP = CP;
        this.city = city;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCP() {
        return CP;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
