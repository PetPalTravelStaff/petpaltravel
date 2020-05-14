package com.petpal.petpaltravel.model;

import com.petpal.petpaltravel.model.Persistance.DataTestDAO;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class PPTModel {
    DataTestDAO myDAO;

    public PPTModel() {
        myDAO= DataTestDAO.getInstance();
    }

    public User validatePassword(String userMail, String userPass) {
        User result= null;
        result= myDAO.validatePassword(userMail, userPass);
        return result;
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
}
