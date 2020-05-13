package com.petpal.petpaltravel.model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class PPTModel {
    List<CompanionForPet> demandsFOR= new ArrayList<CompanionForPet>();
    List<CompanionOfPet> oferingsOF= new ArrayList<CompanionOfPet>();
    List<Location> mylocations = new ArrayList<Location>();;
    List<User> myUsers=  new ArrayList<User>();
    User myUser;
    static PPTModel instancePPT= null;

    private PPTModel() {
        addUsersTest();
        addLocationTest();
        addCompanionOfPet();
        addCompanionForPet();
    }

    static public PPTModel getInstance() {
        if (instancePPT==null) {
            instancePPT= new PPTModel();
        }
        return instancePPT;
    }

    private void addCompanionForPet() {
        demandsFOR.add(new CompanionForPet(1, 1, "Drako", new GregorianCalendar(2020,5,27),
                null, 3, 4, "Perro/a", "FoxTerrier pequeño (se puede llevar en cabina)",
                        0, 3, 0, 0));
        demandsFOR.add(new CompanionForPet(2, 2, "Sibil",
                new GregorianCalendar(2020,6,10), new GregorianCalendar(2020,8,31), 4, 1,
                "Gato/a", "Gatita pequeña muy buena", 0, 0, 0, 0));
        demandsFOR.add(new CompanionForPet(3, 2, "Dina",
                new GregorianCalendar(2020,6,28), new GregorianCalendar(2020,8,31), 4, 2, "Gato/a",
                "Gato mayor, pesa bastante", 0, 4, 0, 0));
    }

    private void addCompanionOfPet() {
    }

    private void addLocationTest() {
    }

    private void addUsersTest() {
        myUsers.add(new User( 1, "Protectora Can", "can@gmail.com", "passcan", "645859626", true, -1));
        myUsers.add(new User(2, "Protectora Kitty", "gatos@gmail.com", "passckitty", "655963626", true, -1));
        myUsers.add(new User( 3, "Marta", "marta@gmail.com", "passmarta", "636261646", false, -1));
        myUsers.add(new User(4, "Roser", "roser@gmail.com", "passroser", "625451245", false, -1));
        myUsers.add(new User(5, "Anna", "anna@gmail.com", "passanna", null, false, -1));
    }


    public User getMyUser() {
        return myUser;
    }

    public void setMyUser(User myUser) {
        this.myUser = myUser;
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
        User result= null;
        for (User u: myUsers) {
            if(userMail.equals(u.getEmail()) & userPass.equals(u.getPassword())){
                result= u;
            }
        }
        return result;
    }

    //return 0 if email already exist
    //return 1 if added
    //return -1 other problem
    public int insertUser(User provUser) {
        int result= 0;
        Boolean control= false;
        Boolean control2= false;
        for (User u: myUsers) {
            if(provUser.getEmail().equals(u.getEmail())){
                control= true;
            }
        }
        if (!control) {
            control2= myUsers.add(provUser);
            if (control2) {
                result=1;
            } else {
                result=-1;
            }
        }
        return result;
    }
}
