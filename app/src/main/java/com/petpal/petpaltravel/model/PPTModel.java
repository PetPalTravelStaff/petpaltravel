package com.petpal.petpaltravel.model;

import android.widget.ArrayAdapter;

import com.petpal.petpaltravel.model.Persistance.DataTestDAO;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class PPTModel {
    DataTestDAO myDAO;
    final String[] transport= {"Avión", "Barco", "Coche", "Tren"};
    final String[] typePets= {"Gato/a", "Perro/a", "Otros tipos"};

    public PPTModel() {
        myDAO= DataTestDAO.getInstance();
    }

    public User validatePassword(String userMail, String userPass) {
        User result= null;
        result= myDAO.validatePassword(userMail, userPass);
        return result;
    }

    public String[] getTransport() {
        return transport;
    }

    public String[] getTypePets() {
        return typePets;
    }

    //return 0 if email already exist
    //return 1 if added
    //return -1 other problem
    public int insertUser(User provUser) {
        int result= 0;
        result= myDAO.insertUser(provUser);
        return result;
    }

    public List<CompanionOfPet> getAllOffers(){
        List<CompanionOfPet> result= null;
        result= myDAO.recoverAllOffers();
        return result;
    }

    public List<CompanionForPet> getAllDemands() {
        List<CompanionForPet> result= null;
        result= myDAO.recoverAllDemands();
        return result;
    }

    public CompanionForPet recoverDemandById(int idDemand) {
        CompanionForPet result= null;
        result= myDAO.searchDemandById(idDemand);
        return result;
    }

    public Boolean addPersonToDemand(int userId, String nameUser, int demandId) {
        Boolean result= false;
        result= myDAO.addPersonToDemand(userId, nameUser, demandId);
        return result;
    }

    public CompanionOfPet recoverOfferById(int idOffer) {
        CompanionOfPet result= null;
        result= myDAO.searchOfferById(idOffer);
        return result;
    }

    public Boolean addShelterToOffer(int userId, String nameUser, int idOffer) {
        Boolean result= false;
        result= myDAO.addShelterToOffer(userId, nameUser, idOffer);
        return result;
    }

    public User searchUserById(int idUser) {
        User result= null;
        result= myDAO.recoverUserById(idUser);
        return result;
    }

    //return 0 if new email already exist
    //return 1 if updated
    //return -1 other problem
    public int updateUser(User newDataUser) {
        int result= 0;
        result= myDAO.updateUser(newDataUser);
        return result;
    }

    public String[] getCities() {
        String[] result= null;
        result= myDAO.recoverCities();
        return result;
    }

    //return 0 if not added
    //return 1 if added
    public int addOfferToBD(CompanionOfPet myOffer) {
        int result= 0;
        result= myDAO.addOffer(myOffer);
        return result;
    }
}

