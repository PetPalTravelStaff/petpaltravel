package com.petpal.petpaltravel.model;

import com.petpal.petpaltravel.model.Persistance.DataTestDAO;

import java.util.List;

public class PPTModel {
    DataTestDAO myDAO;

    //Constructor
    public PPTModel() {
        myDAO= DataTestDAO.getInstance();
    }

    //Getters
    public String[] getTransport() {
        return myDAO.getTransport();
    }

    //-- HELPERS---
    public String[] getCities() {
        String[] result= null;
        result= myDAO.recoverCities();
        return result;
    }


    //----USERS------
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

    public Boolean deleteUser(int idUser) {
        boolean result;
        User myUser= myDAO.recoverUserById(idUser);
        result= myDAO.deleteUser(myUser);
        return result;

    }

    public User searchUserByMail(String userMail) {
        User result= null;
        result= myDAO.recoverUserByMail(userMail);
        return result;
    }

    //--- COMPANION OF PET (OFFER) -----
    public List<CompanionOfPet> getAllOffers(){
        List<CompanionOfPet> result= null;
        result= myDAO.recoverAllOffers();
        return result;
    }

    public List<CompanionOfPet> getOffersPostedByPerson(int iduser){
        List<CompanionOfPet> result= null;
        result= myDAO.recoverOffersOfPerson(iduser);
        return result;
    }

    public CompanionOfPet recoverOfferById(int idOffer) {
        CompanionOfPet result= null;
        result= myDAO.searchOfferById(idOffer);
        return result;
    }

    //return 0 if not added
    //return id number offer if added
    public int addOfferToBD(CompanionOfPet myOffer) {
        int result= 0;
        result= myDAO.addOffer(myOffer);
        return result;
    }

    public Boolean modifyOffer(CompanionOfPet newDataOffer) {
        boolean result= false;
        result= myDAO.modifyOffer(newDataOffer);
        return result;
    }

    public boolean cancelOffer(CompanionOfPet myOffer) {
        boolean result;
        result= myDAO.deleteOffer(myOffer);
        return result;
    }




    //---- COMPANION FOR PET (DEMANDS) ------
    public List<CompanionForPet> getAllDemands() {
        List<CompanionForPet> result= null;
        result= myDAO.recoverAllDemands();
        return result;
    }

    public List<CompanionForPet> getDemandsPostedByShelter(int iduser) {
        List<CompanionForPet> result= null;
        result= myDAO.recoverOffersOfShelter(iduser);
        return result;
    }

    public CompanionForPet recoverDemandById(int idDemand) {
        CompanionForPet result= null;
        result= myDAO.searchDemandById(idDemand);
        return result;
    }

    //return 0 if not added
    //return id number demand if added
    public int addDemandToBD(CompanionForPet myDemand) {
        int result= 0;
        result= myDAO.addDemand(myDemand);
        return result;
    }

    public Boolean modifyDemand(CompanionForPet myDemand) {
        Boolean result= false;
        result= myDAO.modifyDemand(myDemand);
        return result;
    }


    public boolean cancelDemand(CompanionForPet myDemand) {
        boolean result;
        result= myDAO.deleteDemand(myDemand);
        return result;
    }



        //---- APPLICATION FOR OFFER ------

    public int addApplicationToOffer(ApplicationForOffer myApplication) {
        int result= 0;
        result= myDAO.addShelterToOffer(myApplication);
        return result;
    }

    public ApplicationForOffer searchApplicationForOffer(int offerId, int idUser) {
        ApplicationForOffer result= null;
        result= myDAO.recoverApplicationForOffer(offerId, idUser);
        return result;
    }

    public List<ApplicationForOffer> listShelterInterestedByOffer(int idOffer) {
        List<ApplicationForOffer> result= null;
        result= myDAO.recoverApplicationForOneOffer(idOffer);
        return result;
    }

    public ApplicationForOffer searchOfferApplyById(int applyId) {
        ApplicationForOffer result= null;
        result= myDAO.recoverOfferApplyById(applyId);
        return result;
    }

    public Boolean confirmSelectedShelter(ApplicationForOffer myApplication) {
        Boolean result= false;
        result= myDAO.setSeletedShelterInOffer(myApplication);
        return result;
    }

    public Boolean deleteApplicationToOffer(ApplicationForOffer myApplication) {
        Boolean result= false;
        result= myDAO.deleteShelterFromOffer(myApplication);
        return result;
    }

    public Boolean modifyApplicationToOffer(ApplicationForOffer myApplication) {
        Boolean result= false;
        result= myDAO.modifyApplyForOffer(myApplication);
        return result;
    }

    public Boolean unConfirmSelectedShelter(ApplicationForOffer myApplication) {
        Boolean result= false;
        result= myDAO.unSetSeletedShelterInOffer(myApplication);
        return result;
    }

    public boolean rejectAplicationForOffer(ApplicationForOffer myApplication) {
        Boolean result= false;
        result= myDAO.rejectApplyForOffer(myApplication);
        return result;
    }



    //---- APPLICATION FOR DEMAND ------
    public int addApplicationToDemand(ApplicationForDemand myApplication) {
        int result= 0;
        result= myDAO.addPersonToDemand(myApplication);
        return result;
    }

    public ApplicationForDemand searchApplicationForDemand(int demandId, int idUser) {
        ApplicationForDemand result= null;
        result= myDAO.recoverApplicationForDemand(demandId, idUser);
        return result;
    }

    public List<ApplicationForDemand> listPersonInterestedByDemand(int idDemand) {
        List<ApplicationForDemand> result= null;
        result= myDAO.recoverApplicationForOneDemand(idDemand);
        return result;
    }

    public ApplicationForDemand searchDemandApplyById(int applyId) {
        ApplicationForDemand result= null;
        result= myDAO.recoverDemandApplyById(applyId);
        return result;
    }

    public Boolean confirmSelectedShelter(ApplicationForDemand myApplication) {
        Boolean result= false;
        result= myDAO.setSeletedPersonInDemand(myApplication);
        return result;
    }

    public Boolean modifyApplicationToDemand(ApplicationForDemand myApplication) {
        Boolean result= false;
        result= myDAO.modifyApplyForDemand(myApplication);
        return result;
    }


    public Boolean unOfferPersonToDemand(ApplicationForDemand myApplication) {
        Boolean result= false;
        result= myDAO.deleteApplyForDemand(myApplication);
        return result;
    }


    public Boolean rejectAplicationForDemand(ApplicationForDemand myApplication) {
        Boolean result= false;
        result= myDAO.rejectApplyForDemand(myApplication);
        return result;
    }

    public Boolean unConfirmSelectedPerson(ApplicationForDemand myApplication) {
        Boolean result= false;
        result= myDAO.unSetSeletedPersonInDemand(myApplication);
        return result;
    }



}


