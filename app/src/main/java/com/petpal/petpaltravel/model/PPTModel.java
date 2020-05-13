package com.petpal.petpaltravel.model;

import java.util.List;

public class PPTModel {
    List<CompanionForPet> demandsFOR;
    List<CompanionOfPet> oferingsOF;
    User myUser;


    public User getMyUser() {
        return myUser;
    }

    public void setMyUser(User myUser) {
        this.myUser = myUser;
    }



    public PPTModel() {
    }

    public List<CompanionForPet> getDemandsFOR() {
        return demandsFOR;
    }

    public void setDemandsFOR(List<CompanionForPet> demandsFOR) {
        this.demandsFOR = demandsFOR;
    }

    public List<CompanionOfPet> getOferingsOF() {
        return oferingsOF;
    }

    public void setOferingsOF(List<CompanionOfPet> oferingsOF) {
        this.oferingsOF = oferingsOF;
    }


    public User validatePassword(String userMail, String userPass) {
        return null;
    }
}
