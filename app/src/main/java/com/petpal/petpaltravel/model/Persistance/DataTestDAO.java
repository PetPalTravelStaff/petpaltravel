package com.petpal.petpaltravel.model.Persistance;

import com.petpal.petpaltravel.model.ApplicationForDemand;
import com.petpal.petpaltravel.model.ApplicationForOffer;
import com.petpal.petpaltravel.model.CompanionForPet;
import com.petpal.petpaltravel.model.CompanionOfPet;
import com.petpal.petpaltravel.model.Location;
import com.petpal.petpaltravel.model.User;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class DataTestDAO {
    List<CompanionForPet> demandsFOR= new ArrayList<CompanionForPet>();
    List<CompanionOfPet> offeringsOF = new ArrayList<CompanionOfPet>();
    List<Location> myLocations = new ArrayList<Location>();;
    List<User> myUsers=  new ArrayList<User>();
    List<ApplicationForDemand> myApplicationsForDemands =  new ArrayList<ApplicationForDemand>();
    List<ApplicationForOffer> myApplicationsForOffers = new ArrayList<ApplicationForOffer>();
    User myUser;
    static DataTestDAO instanceDAO= null;

    private DataTestDAO() {
        addUsersTest();
        addLocationTest();
        addCompanionOfPet();
        addCompanionForPet();
        addApplicationDemand();
        addApplicationOffer();
    }



    static public DataTestDAO getInstance() {
        if (instanceDAO==null) {
            instanceDAO= new DataTestDAO();
        }
        return instanceDAO;
    }

    private void addCompanionForPet() {
        demandsFOR.add(new CompanionForPet(1, 1, "Protectora Can", "Drako", new GregorianCalendar(2020,5,27),
                null, "Ibiza", "Sta. Cruz de Tenerife", "Perro/a", "FoxTerrier pequeño (se puede llevar en cabina)",
                0, null));
        demandsFOR.add(new CompanionForPet(2, 2, "Protectora Kitty", "Sibil",
                new GregorianCalendar(2020,6,10), new GregorianCalendar(2020,8,31), "Sta. Cruz de Tenerife", "Hospitalet de Llobregat",
                "Gato/a", "Gatita pequeña muy buena", 0, null));
        CompanionForPet test1= new CompanionForPet(3, 2, "Protectora Kitty", "Dina",
                new GregorianCalendar(2020,6,28), new GregorianCalendar(2020,8,31), "Sta. Cruz de Tenerife", "Madrid", "Gato/a",
                "Gato mayor, pesa bastante", 0, null);
        test1.setNamePersonsIntPosition("Roser",0);
        test1.setIdPersonsIntPosition(4,0);
        demandsFOR.add(test1);
        demandsFOR.add(new CompanionForPet(4, 1, "Protectora Can", "Lucy",
                new GregorianCalendar(2020,7,10), new GregorianCalendar(2020,9,12), "Ibiza", "Hospitalet de Llobregat",
                "Cobaya", "Cobaya super mona", 0, null));
    }

    private void addCompanionOfPet() {
        CompanionOfPet test1= new CompanionOfPet(1, 3,  "Marta", new GregorianCalendar(2020,6,10),
                "Hospitalet de Llobregat", "Ibiza","Avión", "Perro/a", "Soy adiestradora canina, así que tengo mucha experiencia con perros",
                0, null);
        test1.setIdShelterIntPosition(2,0);
        test1.setNamesShelterIntPosition("Protectora Kitty",0);
        offeringsOF.add(test1);
        offeringsOF.add(new CompanionOfPet(2, 4,  "Roser", new GregorianCalendar(2020,6,20),
                "Madrid", "Sta. Cruz de Tenerife","Avión", "Perro/a, Gato/a, Otros", null,
                0, null));
        offeringsOF.add(new CompanionOfPet(3, 3,  "Marta", new GregorianCalendar(2020,7,26),
                "Ibiza", "Hospitalet de Llobregat","Avión", "Perro/a", "Soy adiestradora canina, así que tengo mucha experiencia con perros",
                0, null));
    }

    private void addLocationTest() {
        myLocations.add(new Location(1, "08907", "Hospitalet de Llobregat", "Barcelona"));
        myLocations.add(new Location(2, "28001", "Madrid", "Madrid"));
        myLocations.add(new Location(3, "07800", "Ibiza", "Illes Balears"));
        myLocations.add(new Location(4, "38001", "Sta. Cruz de Tenerife", "Sta. Cruz de Tenerife"));
    }

    private void addUsersTest() {
        myUsers.add(new User( 1, "Protectora Can", "can@gmail.com", "passcan", "645859626", true, -1));
        myUsers.add(new User(2, "Protectora Kitty", "gatos@gmail.com", "passkitty", "655963626", true, -1));
        myUsers.add(new User( 3, "Marta", "marta@gmail.com", "passmarta", "636261646", false, -1));
        myUsers.add(new User(4, "Roser", "roser@gmail.com", "passroser", "625451245", false, -1));
        myUsers.add(new User(5, "Anna", "anna@gmail.com", "passanna", null, false, -1));
    }

    private void addApplicationDemand() {
        myApplicationsForDemands.add(new ApplicationForDemand(1, 1,4,"Roser",
                "Barco", null, "roser@gmail.com","625451245", false ));
    }

    private void addApplicationOffer() {
        myApplicationsForOffers.add(new ApplicationForOffer(1, 2,2,"Protectora Kitty", "Gato/a",
                "Blanquita", null, "gatos@gmail.com","655963626", false ));
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

    public List<CompanionOfPet> getOfferingsOF() {
        return offeringsOF;
    }

    public void setOfferingsOF(List<CompanionOfPet> offeringsOF) {
        this.offeringsOF = offeringsOF;
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
        //verify first if this mail is already un BBDD
        for (User u: myUsers) {
            if(provUser.getEmail().equals(u.getEmail())){
                control= true;
            }
        }
        //if not, try to add
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

    public List<CompanionOfPet> recoverAllOffers() {
        return offeringsOF;
    }

    public List<CompanionForPet> recoverAllDemands() {
        return demandsFOR;
    }

    public CompanionForPet searchDemandById(int idDemand) {
        CompanionForPet result= null;
        for (CompanionForPet demand : demandsFOR) {
            if (idDemand==demand.getId()){
                result= demand;
            }
        }
        return result;
    }

    public Boolean addPersonToDemand(ApplicationForDemand myApplication) {
        Boolean result= false;
        int lastIndex= myApplicationsForDemands.size()-1;
        for (CompanionForPet demand : demandsFOR) {
            if (myApplication.getIdDemand() == demand.getId()) {
                if (demand.getIdPersonInterestePosition(0) == 0) {
                    demand.setIdPersonsIntPosition(myApplication.getIdPersonApplying(),0);
                    demand.setNamePersonsIntPosition(myApplication.getNamePerson(),0);
                    myApplication.setIdApplForDem(lastIndex+1);
                    myApplicationsForDemands.add(myApplication);
                    result = true;
                } else if (demand.getIdPersonInterestePosition(1) == 0) {
                    demand.setIdPersonsIntPosition(myApplication.getIdPersonApplying(),1);
                    demand.setNamePersonsIntPosition(myApplication.getNamePerson(),1);
                    myApplication.setIdApplForDem(lastIndex+1);
                    myApplicationsForDemands.add(myApplication);
                    result = true;
                } else if (demand.getIdPersonInterestePosition(2) == 0) {
                    demand.setIdPersonsIntPosition(myApplication.getIdPersonApplying(),2);
                    demand.setNamePersonsIntPosition(myApplication.getNamePerson(),2);
                    myApplication.setIdApplForDem(lastIndex+1);
                    myApplicationsForDemands.add(myApplication);
                    result = true;
                }
            }
        }
        return result;
    }

    public CompanionOfPet searchOfferById(int idOffer) {
        CompanionOfPet result= null;
        for (CompanionOfPet offer : offeringsOF) {
            if (idOffer==offer.getId()){
                result= offer;
            }
        }
        return result;
    }

    public Boolean addShelterToOffer(ApplicationForOffer myApplication) {
        Boolean result= false;
        int lastIndex= myApplicationsForOffers.size()-1;
        for (CompanionOfPet offer : offeringsOF) {
            if (myApplication.getIdOffer() == offer.getId()) {
                if (offer.getIdShelterIntPosition(0) == 0) {
                    offer.setIdShelterIntPosition(myApplication.getIdShelterApplying(),0);
                    offer.setNamesShelterIntPosition(myApplication.getNameShelter(),0);
                    myApplication.setIdAppliForOf(lastIndex+1);
                    myApplicationsForOffers.add(myApplication);
                    result = true;
                } else if (offer.getIdShelterIntPosition(1) == 0) {
                    offer.setIdShelterIntPosition(myApplication.getIdShelterApplying(),1);
                    offer.setNamesShelterIntPosition(myApplication.getNameShelter(),1);
                    myApplication.setIdAppliForOf(lastIndex+1);
                    myApplicationsForOffers.add(myApplication);
                    result = true;
                } else if (offer.getIdShelterIntPosition(2) == 0) {
                    offer.setIdShelterIntPosition(myApplication.getIdShelterApplying(),2);
                    offer.setNamesShelterIntPosition(myApplication.getNameShelter(),2);
                    myApplication.setIdAppliForOf(lastIndex+1);
                    myApplicationsForOffers.add(myApplication);
                    result = true;
                }
            }
        }
        return result;
    }

    public User recoverUserById(int idUser) {
        User result= null;
        for (User u: myUsers) {
            if(idUser==u.getId()) {
                result= u;
            }
        }
        return result;
    }


    //return 0 if email already exist in other user
    //return 1 if user updated
    //return -1 other problem
    public int updateUser(User newDataUser) {
        int result= 0;
        Boolean control= false;
        Boolean control2= false;
        for (User u: myUsers) {
            //verify first if this mail exist in data base
            if(newDataUser.getEmail().equals(u.getEmail())) {
                //if is of the user, everything is ok, so set false
                if (newDataUser.getId() == u.getId()) {
                    control = false;
                    //if it is of other user, can not change so set true;
                } else {
                    control = true;
                }
            }
        }
        //If no other has this mail...
        if (!control) {
            for (User us : myUsers) {
                //when find this user
                if (newDataUser.getId() == us.getId()) {
                    us.setName(newDataUser.getName());
                    us.setEmail(newDataUser.getEmail());
                    us.setPhone(newDataUser.getPhone());
                    if (null!=newDataUser.getPassword()){
                        us.setPassword(newDataUser.getPassword());
                    }
                }
            }
        }
        //If all data is now eaual in newDataUser and data of user in BBDD result 1, otherwise -1
        if (recoverUserById(newDataUser.getId()).getName().equals(newDataUser.getName()) &
                recoverUserById(newDataUser.getId()).getEmail().equals(newDataUser.getEmail()) &
                recoverUserById(newDataUser.getId()).getPhone().equals(newDataUser.getPhone())){
                if (null!= newDataUser.getPassword()) {
                    if (recoverUserById(newDataUser.getId()).getPassword().equals(newDataUser.getPassword())) {
                        result=1;
                    } else {
                        result=-1;
                    }
                } else {
                    result=1;
                }
        } else {
            result=-1;
        }

        return result;
    }

    public String[] recoverCities() {
        String[] result=new String[myLocations.size()];
        for (int i=0; i<myLocations.size();i++) {
            result[i]= myLocations.get(i).getCity();
            }
        return result;
    }

    //return 0 if not added
    //return numer of offer if added
    public int addOffer(CompanionOfPet myOffer) {
        int result= 0;
        myOffer.setId(0);
        int lastID= offeringsOF.get(offeringsOF.size()-1).getId();
        myOffer.setId(lastID+1);
        offeringsOF.add(myOffer);
        return offeringsOF.get(offeringsOF.size()-1).getId();
    }

    //return 0 if not added
    //return numer of demand if added
    public int addDemand(CompanionForPet myDemand) {
        int result= 0;
        myDemand.setId(0);
        int lastID= demandsFOR.get(demandsFOR.size()-1).getId();
        myDemand.setId(lastID+1);
        demandsFOR.add(myDemand);
        return demandsFOR.get(demandsFOR.size()-1).getId();
    }

    public List<CompanionOfPet> recoverOffersOfPerson(int iduser) {
        List<CompanionOfPet> result= new ArrayList<CompanionOfPet>();
        for (CompanionOfPet of : offeringsOF) {
            if (iduser == of.getIdeUserPersonOffering()) {
                result.add(of);
            }
        }
        return result;
    }

    public List<CompanionForPet> recoverOffersOfShelter(int iduser) {
        List<CompanionForPet> result= new ArrayList<CompanionForPet>();
        for (CompanionForPet dem : demandsFOR) {
            if (iduser == dem.getIdeUserShelterOffering()) {
                result.add(dem);
            }
        }
        return result;
    }



    public ApplicationForOffer recoverApplicationForOffer(int offerId, int idUser) {
        ApplicationForOffer result= null;
        for (ApplicationForOffer apliForOf: myApplicationsForOffers) {
            if (apliForOf.getIdOffer()==offerId & apliForOf.getIdShelterApplying()==idUser) {
                result= apliForOf;
            }
        }
        return result;
    }

    public ApplicationForDemand recoverApplicationForDemand(int demandId, int idUser) {
        ApplicationForDemand result= null;
        for (ApplicationForDemand apliForDe: myApplicationsForDemands) {
            if (apliForDe.getIdDemand()==demandId & apliForDe.getIdPersonApplying()==idUser) {
                result= apliForDe;
            }
        }
        return result;
    }

    public List<ApplicationForDemand> recoverApplicationForOneDemand(int idDemand) {
        List<ApplicationForDemand> result= new ArrayList<ApplicationForDemand>();
        for (ApplicationForDemand apli : myApplicationsForDemands) {
            if (idDemand == apli.getIdDemand()) {
                result.add(apli);
            }
        }
        return result;
    }

    public List<ApplicationForOffer> recoverApplicationForOneOffer(int idOffer) {
        List<ApplicationForOffer> result= new ArrayList<ApplicationForOffer>();
        for (ApplicationForOffer apli : myApplicationsForOffers) {
            if (idOffer == apli.getIdOffer()) {
                result.add(apli);
            }
        }
        return result;
    }
}
