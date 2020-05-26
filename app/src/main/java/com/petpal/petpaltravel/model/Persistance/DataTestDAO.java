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
    List<CompanionForPet> demandsFOR = new ArrayList<CompanionForPet>();
    List<CompanionOfPet> offeringsOF = new ArrayList<CompanionOfPet>();
    List<Location> myLocations = new ArrayList<Location>();
    List<User> myUsers = new ArrayList<User>();
    List<ApplicationForDemand> myApplicationsForDemands = new ArrayList<ApplicationForDemand>();
    List<ApplicationForOffer> myApplicationsForOffers = new ArrayList<ApplicationForOffer>();
    User myUser;
    static DataTestDAO instanceDAO = null;

    private DataTestDAO() {
        addUsersTest();
        addLocationTest();
        addCompanionOfPet();
        addCompanionForPet();
        addApplicationDemand();
        addApplicationOffer();
    }

    static public DataTestDAO getInstance() {
        if (instanceDAO == null) {
            instanceDAO = new DataTestDAO();
        }
        return instanceDAO;
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


    //-------- DATA TEST-------------
    private void addCompanionForPet() {
        demandsFOR.add(new CompanionForPet(1, 1, "Protectora Can", "Drako", new GregorianCalendar(2020, 5, 27),
                null, "Ibiza", "Sta. Cruz de Tenerife", "Perro/a", "FoxTerrier pequeño (se puede llevar en cabina)",
                4, "Roser"));
        demandsFOR.add(new CompanionForPet(2, 2, "Protectora Kitty", "Sibil",
                new GregorianCalendar(2020, 6, 10), new GregorianCalendar(2020, 8, 31), "Sta. Cruz de Tenerife", "Hospitalet de Llobregat",
                "Gato/a", "Gatita pequeña muy buena", 0, null));
        CompanionForPet test1 = new CompanionForPet(3, 2, "Protectora Kitty", "Dina",
                new GregorianCalendar(2020, 6, 28), new GregorianCalendar(2020, 8, 31), "Sta. Cruz de Tenerife", "Madrid", "Gato/a",
                "Gato mayor, pesa bastante", 0, null);
        test1.setNamePersonsIntPosition("Roser", 0);
        test1.setIdPersonsIntPosition(4, 0);
        demandsFOR.add(test1);
        demandsFOR.add(new CompanionForPet(4, 1, "Protectora Can", "Lucy",
                new GregorianCalendar(2020, 7, 10), new GregorianCalendar(2020, 9, 12), "Ibiza", "Hospitalet de Llobregat",
                "Cobaya", "Cobaya super mona", 0, null));
    }

    private void addCompanionOfPet() {
        CompanionOfPet test1 = new CompanionOfPet(1, 3, "Marta", new GregorianCalendar(2020, 6, 10),
                "Hospitalet de Llobregat", "Ibiza", "Avión", "Perro/a", "Soy adiestradora canina, así que tengo mucha experiencia con perros",
                0, null);
        test1.setIdShelterIntPosition(1, 0);
        test1.setNamesShelterIntPosition("Protectora Can", 0);
        offeringsOF.add(test1);
        CompanionOfPet test2 =new CompanionOfPet(2, 4, "Roser", new GregorianCalendar(2020, 6, 20),
                "Madrid", "Sta. Cruz de Tenerife", "Avión", "Perro/a, Gato/a, Otros", null,
                0, null);
        test2.setIdShelterIntPosition(2, 0);
        test2.setNamesShelterIntPosition("Protectora Kitty", 0);
        offeringsOF.add(test2);
        offeringsOF.add(new CompanionOfPet(3, 3, "Marta", new GregorianCalendar(2020, 7, 26),
                "Ibiza", "Hospitalet de Llobregat", "Avión", "Perro/a", "Soy adiestradora canina, así que tengo mucha experiencia con perros",
                0, null));
    }

    private void addLocationTest() {
        myLocations.add(new Location(1, "08907", "Hospitalet de Llobregat", "Barcelona"));
        myLocations.add(new Location(2, "28001", "Madrid", "Madrid"));
        myLocations.add(new Location(3, "07800", "Ibiza", "Illes Balears"));
        myLocations.add(new Location(4, "38001", "Sta. Cruz de Tenerife", "Sta. Cruz de Tenerife"));
    }

    private void addUsersTest() {
        myUsers.add(new User(1, "Protectora Can", "can@gmail.com", "passcan", "645859626", true, -1));
        myUsers.add(new User(2, "Protectora Kitty", "gatos@gmail.com", "passkitty", "655963626", true, -1));
        myUsers.add(new User(3, "Marta", "marta@gmail.com", "passmarta", "636261646", false, -1));
        myUsers.add(new User(4, "Roser", "roser@gmail.com", "passroser", "625451245", false, -1));
        myUsers.add(new User(5, "Anna", "anna@gmail.com", "passanna", null, false, -1));
    }

    private void addApplicationDemand() {
        myApplicationsForDemands.add(new ApplicationForDemand(1, 1, 4, "Roser",
                "Barco", null, "roser@gmail.com", "625451245", true));
        myApplicationsForDemands.add(new ApplicationForDemand(2, 3, 4, "Roser",
                "Avión", null, "roser@gmail.com", "625451245", false));
    }

    private void addApplicationOffer() {
        myApplicationsForOffers.add(new ApplicationForOffer(1, 2, 2, "Protectora Kitty", "Gato/a",
                "Blanquita", null, "gatos@gmail.com", "655963626", false));
        myApplicationsForOffers.add(new ApplicationForOffer(2, 3, 1, "Protectora Can", "Perro/a",
                "Blake", null, "can@gmail.com", "645859626", false));
    }


    //-------- HELPERS -------------
    public String[] recoverCities() {
        String[] result = new String[myLocations.size()];
        for (int i = 0; i < myLocations.size(); i++) {
            result[i] = myLocations.get(i).getCity();
        }
        return result;
    }


    //-------- USERS -------------

    public User validatePassword(String userMail, String userPass) {
        User result = null;
        for (User u : myUsers) {
            if (userMail.equals(u.getEmail()) & userPass.equals(u.getPassword())) {
                result = u;
            }
        }
        return result;
    }

    //return 0 if email already exist
    //return 1 if added
    //return -1 other problem
    public int insertUser(User provUser) {
        int result = 0;
        Boolean control = false;
        Boolean control2 = false;
        //verify first if this mail is already un BBDD
        for (User u : myUsers) {
            if (provUser.getEmail().equals(u.getEmail())) {
                control = true;
            }
        }
        //if not, try to add
        if (!control) {
            int lastId= myUsers.size();
            provUser.setId(lastId+1);
            control2 = myUsers.add(provUser);
            if (control2) {
                result = 1;
            } else {
                result = -1;
            }
        }
        return result;
    }

    public User recoverUserById(int idUser) {
        User result = null;
        for (User u : myUsers) {
            if (idUser == u.getId()) {
                result = u;
            }
        }
        return result;
    }

    //return 0 if email already exist in other user
    //return 1 if user updated
    //return -1 other problem
    public int updateUser(User newDataUser) {
        int result = 0;
        Boolean control = false;
        Boolean control2 = false;
        for (User u : myUsers) {
            //verify first if this mail exist in data base
            if (newDataUser.getEmail().equals(u.getEmail())) {
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
                    if (null != newDataUser.getPassword()) {
                        us.setPassword(newDataUser.getPassword());
                    }
                }
            }
        }
        //If all data is now eaual in newDataUser and data of user in BBDD result 1, otherwise -1
        if (recoverUserById(newDataUser.getId()).getName().equals(newDataUser.getName()) &
                recoverUserById(newDataUser.getId()).getEmail().equals(newDataUser.getEmail()) &
                recoverUserById(newDataUser.getId()).getPhone().equals(newDataUser.getPhone())) {
            if (null != newDataUser.getPassword()) {
                if (recoverUserById(newDataUser.getId()).getPassword().equals(newDataUser.getPassword())) {
                    result = 1;
                } else {
                    result = -1;
                }
            } else {
                result = 1;
            }
        } else {
            result = -1;
        }

        return result;
    }

    public boolean deleteUser(User myUser) {
        boolean result= false;
        if (myUser.isShelter()){ //If is shelter
            //DELETE ALL PLACES WHERE HIS/HER DATA CAN BE
            //demand posted
            List<CompanionForPet> prov=new ArrayList<>();
            for (CompanionForPet dem: demandsFOR){
                if (dem.getIdeUserShelterOffering()==myUser.getId()) {
                    prov.add(dem);
                }
            }
            for (CompanionForPet dem: prov){
                demandsFOR.remove(dem);
            }

            //data in offers that has applied
            for (CompanionOfPet off: offeringsOF) {
                //selected
                if (off.getIdUserShelterSeleted()==myUser.getId()){
                    off.setNameSSelected("null");
                    off.setIdUserShelterSeleted(0);
                }
                //just application
                if (off.getIdShelterIntPosition(0) ==myUser.getId()) {
                    off.setIdShelterIntPosition(0, 0);
                    off.setNamesShelterIntPosition(null, 0);
                } else if (off.getIdShelterIntPosition(1) == myUser.getId()) {
                    off.setIdShelterIntPosition(0, 1);
                    off.setNamesShelterIntPosition(null, 1);
                } else if (off.getIdShelterIntPosition(2) == myUser.getId()) {
                    off.setIdShelterIntPosition(0, 2);
                    off.setNamesShelterIntPosition(null, 2);
                }
            }
            //application to offers posted
            List<ApplicationForOffer> prov2=new ArrayList<>();
            for (ApplicationForOffer apply: myApplicationsForOffers){
                if (apply.getIdShelterApplying()==myUser.getId()){
                    prov2.add(apply);
                }
            }
            for (ApplicationForOffer apply: prov2){
                myApplicationsForOffers.remove(apply);
            }
        } else { //if is person
            //DELETE ALL PLACES WHERE HIS/HER DATA CAN BE
            //offer posted
            List<CompanionOfPet> prov= new ArrayList<>();
            for (CompanionOfPet off: offeringsOF){
                if (off.getIdeUserPersonOffering()==myUser.getId()) {
                    prov.add(off);
                }
            }
            for (CompanionOfPet off: prov){
                offeringsOF.remove(off);
            }
            //data in demands that has applied
            for (CompanionForPet dem: demandsFOR) {
                //selected
                if (dem.getIdUserPersonSeleted()==myUser.getId()){
                    dem.setNamePSelected("null");
                    dem.setIdUserPersonSeleted(0);
                }
                //just application
                if (dem.getIdPersonInterestePosition(0) ==myUser.getId()) {
                    dem.setIdPersonsIntPosition(0, 0);
                    dem.setNamePersonsIntPosition(null, 0);
                } else if (dem.getIdPersonInterestePosition(1) == myUser.getId()) {
                    dem.setIdPersonsIntPosition(0, 1);
                    dem.setNamePersonsIntPosition(null, 1);
                } else if (dem.getIdPersonInterestePosition(2) == myUser.getId()) {
                    dem.setIdPersonsIntPosition(0, 2);
                    dem.setNamePersonsIntPosition(null, 2);
                }
            }
            //application to dem posted
            List<ApplicationForDemand> prov2= new ArrayList<>();
            for (ApplicationForDemand apply: myApplicationsForDemands){
                if (apply.getIdPersonApplying()==myUser.getId()){
                    prov2.add(apply);
                }
            }
            for (ApplicationForDemand apply: prov2){
                myApplicationsForDemands.remove(apply);
            }
        }
        result= myUsers.remove(myUser);
        return result;
    }

    public User recoverUserByMail(String userMail) {
        User result = null;
        for (User u : myUsers) {
            if (u.getEmail().equals(userMail)) {
                result = u;
            }
        }
        return result;
    }


    //-------- COMPANION OF PET (OFFERS) -------------
    public List<CompanionOfPet> recoverAllOffers() {
        return offeringsOF;
    }

    public CompanionOfPet searchOfferById(int idOffer) {
        CompanionOfPet result = null;
        for (CompanionOfPet offer : offeringsOF) {
            if (idOffer == offer.getId()) {
                result = offer;
            }
        }
        return result;
    }

    //return 0 if not added
    //return numer of offer if added
    public int addOffer(CompanionOfPet myOffer) {
        int result = 0;
        myOffer.setId(0);
        int lastID = offeringsOF.get(offeringsOF.size() - 1).getId();
        myOffer.setId(lastID + 1);
        offeringsOF.add(myOffer);
        return offeringsOF.get(offeringsOF.size() - 1).getId();
    }


    public List<CompanionOfPet> recoverOffersOfPerson(int iduser) {
        List<CompanionOfPet> result = new ArrayList<CompanionOfPet>();
        for (CompanionOfPet of : offeringsOF) {
            if (iduser == of.getIdeUserPersonOffering()) {
                result.add(of);
            }
        }
        return result;
    }

    public Boolean modifyOffer(CompanionOfPet myOffer) {
        Boolean result = false;
        for (CompanionOfPet offer : offeringsOF) {
            if (myOffer.getId() == offer.getId()) {
                offer = myOffer;
                result = true;
            }
        }
        return result;
    }

    public boolean deleteOffer(CompanionOfPet myOffer) {
        boolean result;
        if (offeringsOF.contains(myOffer)) {
            offeringsOF.remove(myOffer);
            //if deleted: contains will be false, so it is true that is deleted
            //if not deleted: contains will be true, so it is false that is deleted
            result = !offeringsOF.contains(myOffer);
            List<ApplicationForOffer> applyToRemove = recoverApplicationForOneOffer(myOffer.getId());
            for (ApplicationForOffer apli : applyToRemove) {
                myApplicationsForOffers.remove(apli);
            }
        } else {
            result = false;
        }
        return result;
    }


    //-------- COMPANION FOR PET (DEMANDS) -------------
    public List<CompanionForPet> recoverAllDemands() {
        return demandsFOR;
    }

    public CompanionForPet searchDemandById(int idDemand) {
        CompanionForPet result = null;
        for (CompanionForPet demand : demandsFOR) {
            if (idDemand == demand.getId()) {
                result = demand;
            }
        }
        return result;
    }

    //return 0 if not added
    //return numer of demand if added
    public int addDemand(CompanionForPet myDemand) {
        int result = 0;
        myDemand.setId(0);
        int lastID = demandsFOR.get(demandsFOR.size() - 1).getId();
        myDemand.setId(lastID + 1);
        demandsFOR.add(myDemand);
        return demandsFOR.get(demandsFOR.size() - 1).getId();
    }

    public Boolean modifyDemand(CompanionForPet myDemand) {
        Boolean result = false;
        for (CompanionForPet dem : demandsFOR) {
            if (myDemand.getId() == dem.getId()) {
                dem = myDemand;
                result = true;
            }
        }
        return result;
    }


    public List<CompanionForPet> recoverOffersOfShelter(int iduser) {
        List<CompanionForPet> result = new ArrayList<CompanionForPet>();
        for (CompanionForPet dem : demandsFOR) {
            if (iduser == dem.getIdeUserShelterOffering()) {
                result.add(dem);
            }
        }
        return result;
    }

    public boolean deleteDemand(CompanionForPet myDemand) {
        boolean result;
        if (demandsFOR.contains(myDemand)) {
            demandsFOR.remove(myDemand);
            //if deleted: contains will be false, so it is true that is deleted
            //if not deleted: contains will be true, so it is false that is deleted
            result = !offeringsOF.contains(myDemand);
            List<ApplicationForDemand> applyToRemove = recoverApplicationForOneDemand(myDemand.getId());
            for (ApplicationForDemand apli : applyToRemove) {
                myApplicationsForDemands.remove(apli);
            }
        } else {
            result = false;
        }

        return result;
    }


    //-------- APPLICATION FOR ASKING A COMPANION FOR PET (APPLICATION FOR DEMAND) -------------
    public int addPersonToDemand(ApplicationForDemand myApplication) {
        int result = 0;
        int lastIndex = myApplicationsForDemands.size();
        for (CompanionForPet demand : demandsFOR) {
            if (myApplication.getIdDemand() == demand.getId()) {
                if (demand.getIdPersonInterestePosition(0) == 0) {
                    demand.setIdPersonsIntPosition(myApplication.getIdPersonApplying(), 0);
                    demand.setNamePersonsIntPosition(myApplication.getNamePerson(), 0);
                    myApplication.setIdApplForDem(lastIndex + 1);
                    myApplicationsForDemands.add(myApplication);
                    result = myApplicationsForDemands.size();
                } else if (demand.getIdPersonInterestePosition(1) == 0) {
                    demand.setIdPersonsIntPosition(myApplication.getIdPersonApplying(), 1);
                    demand.setNamePersonsIntPosition(myApplication.getNamePerson(), 1);
                    myApplication.setIdApplForDem(lastIndex + 1);
                    myApplicationsForDemands.add(myApplication);
                    result = myApplicationsForDemands.size();
                } else if (demand.getIdPersonInterestePosition(2) == 0) {
                    demand.setIdPersonsIntPosition(myApplication.getIdPersonApplying(), 2);
                    demand.setNamePersonsIntPosition(myApplication.getNamePerson(), 2);
                    myApplication.setIdApplForDem(lastIndex + 1);
                    myApplicationsForDemands.add(myApplication);
                    result = myApplicationsForDemands.size();
                }
            }
        }
        return result;
    }

    public ApplicationForDemand recoverApplicationForDemand(int demandId, int idUser) {
        ApplicationForDemand result = null;
        for (ApplicationForDemand apliForDe : myApplicationsForDemands) {
            if (apliForDe.getIdDemand() == demandId & apliForDe.getIdPersonApplying() == idUser) {
                result = apliForDe;
            }
        }
        return result;
    }

    public List<ApplicationForDemand> recoverApplicationForOneDemand(int idDemand) {
        List<ApplicationForDemand> result = new ArrayList<ApplicationForDemand>();
        for (ApplicationForDemand apli : myApplicationsForDemands) {
            if (idDemand == apli.getIdDemand()) {
                result.add(apli);
            }
        }
        return result;
    }

    public ApplicationForDemand recoverDemandApplyById(int applyId) {
        ApplicationForDemand result = null;
        for (ApplicationForDemand apliForDe : myApplicationsForDemands) {
            if (apliForDe.getIdApplForDem() == applyId) {
                result = apliForDe;
            }
        }
        return result;
    }

    public Boolean setSeletedPersonInDemand(ApplicationForDemand myApplication) {
        Boolean result = false;
        for (ApplicationForDemand app : myApplicationsForDemands) {
            if (app.getIdApplForDem() == myApplication.getIdApplForDem()) {
                app.setChoosed(true);
                for (CompanionForPet dem : demandsFOR) {
                    if (dem.getId() == myApplication.getIdDemand()) {
                        dem.setIdUserPersonSeleted(myApplication.getIdPersonApplying());
                        dem.setNamePSelected(myApplication.getNamePerson());
                        result = true;
                    }
                }
            }
        }
        return result;
    }


    public Boolean modifyApplyForDemand(ApplicationForDemand myApplication) {
        Boolean result = false;
        for (ApplicationForDemand app : myApplicationsForDemands) {
            if (app.getIdApplForDem() == myApplication.getIdApplForDem()) {
                app = myApplication;
                result = true;
            }
        }
        return result;
    }

    public Boolean deleteApplyForDemand(ApplicationForDemand myApplication) {
        Boolean result = false;
        for (CompanionForPet demand : demandsFOR) {
            if (myApplication.getIdDemand() == demand.getId()) {
                if (demand.getIdPersonInterestePosition(0) == myApplication.getIdPersonApplying()) {
                    demand.setIdPersonsIntPosition(0, 0);
                    demand.setNamePersonsIntPosition(null, 0);
                    if (myApplicationsForDemands.remove(myApplication)) {
                        result = true;
                    }
                } else if (demand.getIdPersonInterestePosition(1) == myApplication.getIdPersonApplying()) {
                    demand.setIdPersonsIntPosition(0, 1);
                    demand.setNamePersonsIntPosition(null, 1);
                    if (myApplicationsForDemands.remove(myApplication)) {
                        result = true;
                    }
                } else if (demand.getIdPersonInterestePosition(2) == myApplication.getIdPersonApplying()) {
                    demand.setIdPersonsIntPosition(0, 2);
                    demand.setNamePersonsIntPosition(null, 2);
                    if (myApplicationsForDemands.remove(myApplication)) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    public Boolean rejectApplyForDemand(ApplicationForDemand myApplication) {
        Boolean result = false;
        for (CompanionForPet demand : demandsFOR) {
            if (myApplication.getIdDemand() == demand.getId()) {
                if (demand.getIdPersonInterestePosition(0) == myApplication.getIdPersonApplying()) {
                    demand.setIdPersonsIntPosition(0, 0);
                    demand.setNamePersonsIntPosition(null, 0);
                } else if (demand.getIdPersonInterestePosition(1) == myApplication.getIdPersonApplying()) {
                    demand.setIdPersonsIntPosition(0, 1);
                    demand.setNamePersonsIntPosition(null, 1);
                } else if (demand.getIdPersonInterestePosition(2) == myApplication.getIdPersonApplying()) {
                    demand.setIdPersonsIntPosition(0, 2);
                    demand.setNamePersonsIntPosition(null, 2);
                }
            }
        }
        if (myApplicationsForDemands.remove(myApplication)) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }


    public Boolean unSetSeletedPersonInDemand(ApplicationForDemand myApplication) {
        Boolean result = false;
        for (ApplicationForDemand app : myApplicationsForDemands) {
            if (app.getIdApplForDem() == myApplication.getIdApplForDem()) {
                app.setChoosed(false);
                for (CompanionForPet dem : demandsFOR) {
                    if (dem.getId() == myApplication.getIdDemand()) {
                        dem.setIdUserPersonSeleted(0);
                        dem.setNamePSelected(null);
                        result = true;
                    }
                }
            }
        }
        return result;
    }


    //-------- APPLICATION FOR BEING A COMPANION OF PET (APPLICATION FOR OFFER) -------------
    public int addShelterToOffer(ApplicationForOffer myApplication) {
        int result = 0;
        int lastIndex = myApplicationsForOffers.size();
        for (CompanionOfPet offer : offeringsOF) {
            if (myApplication.getIdOffer() == offer.getId()) {
                if (offer.getIdShelterIntPosition(0) == 0) {
                    offer.setIdShelterIntPosition(myApplication.getIdShelterApplying(), 0);
                    offer.setNamesShelterIntPosition(myApplication.getNameShelter(), 0);
                    myApplication.setIdAppliForOf(lastIndex + 1);
                    myApplicationsForOffers.add(myApplication);
                    result = myApplicationsForOffers.size();
                } else if (offer.getIdShelterIntPosition(1) == 0) {
                    offer.setIdShelterIntPosition(myApplication.getIdShelterApplying(), 1);
                    offer.setNamesShelterIntPosition(myApplication.getNameShelter(), 1);
                    myApplication.setIdAppliForOf(lastIndex + 1);
                    myApplicationsForOffers.add(myApplication);
                    result = myApplicationsForOffers.size();
                } else if (offer.getIdShelterIntPosition(2) == 0) {
                    offer.setIdShelterIntPosition(myApplication.getIdShelterApplying(), 2);
                    offer.setNamesShelterIntPosition(myApplication.getNameShelter(), 2);
                    myApplication.setIdAppliForOf(lastIndex + 1);
                    myApplicationsForOffers.add(myApplication);
                    result = myApplicationsForOffers.size();
                }
            }
        }
        return result;
    }


    public ApplicationForOffer recoverApplicationForOffer(int offerId, int idUser) {
        ApplicationForOffer result = null;
        for (ApplicationForOffer apliForOf : myApplicationsForOffers) {
            if (apliForOf.getIdOffer() == offerId & apliForOf.getIdShelterApplying() == idUser) {
                result = apliForOf;
            }
        }
        return result;
    }


    public List<ApplicationForOffer> recoverApplicationForOneOffer(int idOffer) {
        List<ApplicationForOffer> result = new ArrayList<ApplicationForOffer>();
        for (ApplicationForOffer apli : myApplicationsForOffers) {
            if (idOffer == apli.getIdOffer()) {
                result.add(apli);
            }
        }
        return result;
    }


    public ApplicationForOffer recoverOfferApplyById(int applyId) {
        ApplicationForOffer result = null;
        for (ApplicationForOffer apliForOf : myApplicationsForOffers) {
            if (apliForOf.getIdAppliForOf() == applyId) {
                result = apliForOf;
            }
        }
        return result;
    }

    public Boolean setSeletedShelterInOffer(ApplicationForOffer myApplication) {
        Boolean result = false;
        for (ApplicationForOffer app : myApplicationsForOffers) {
            if (app.getIdAppliForOf() == myApplication.getIdAppliForOf()) {
                app.setChoosed(true);
                for (CompanionOfPet offe : offeringsOF) {
                    if (offe.getId() == myApplication.getIdOffer()) {
                        offe.setIdUserShelterSeleted(myApplication.getIdShelterApplying());
                        offe.setNameSSelected(myApplication.getNameShelter());
                        result = true;
                    }
                }
            }
        }
        return result;
    }


    public boolean deleteShelterFromOffer(ApplicationForOffer myApplication) {
        boolean result = false;
        for (CompanionOfPet offer : offeringsOF) {
            if (myApplication.getIdOffer() == offer.getId()) {
                if (offer.getIdShelterIntPosition(0) == myApplication.getIdShelterApplying()) {
                    offer.setIdShelterIntPosition(0, 0);
                    offer.setNamesShelterIntPosition(null, 0);
                    if (myApplicationsForOffers.remove(myApplication)) {
                        result = true;
                    }
                    ;
                } else if (offer.getIdShelterIntPosition(1) == myApplication.getIdShelterApplying()) {
                    offer.setIdShelterIntPosition(0, 1);
                    offer.setNamesShelterIntPosition(null, 1);
                    if (myApplicationsForOffers.remove(myApplication)) {
                        result = true;
                    }
                    ;
                } else if (offer.getIdShelterIntPosition(2) == myApplication.getIdShelterApplying()) {
                    offer.setIdShelterIntPosition(0, 2);
                    offer.setNamesShelterIntPosition(null, 2);
                    if (myApplicationsForOffers.remove(myApplication)) {
                        result = true;
                    }
                    ;
                }
            }
        }
        return result;
    }


    public Boolean modifyApplyForOffer(ApplicationForOffer myApplication) {
        Boolean result = false;
        for (ApplicationForOffer app : myApplicationsForOffers) {
            if (app.getIdAppliForOf() == myApplication.getIdAppliForOf()) {
                app = myApplication;
                result = true;
            }
        }
        return result;
    }

    public Boolean unSetSeletedShelterInOffer(ApplicationForOffer myApplication) {
        Boolean result = false;
        for (ApplicationForOffer app : myApplicationsForOffers) {
            if (app.getIdAppliForOf() == myApplication.getIdAppliForOf()) {
                app.setChoosed(false);
                for (CompanionOfPet offe : offeringsOF) {
                    if (offe.getId() == myApplication.getIdOffer()) {
                        offe.setIdUserShelterSeleted(0);
                        offe.setNameSSelected(null);
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    public Boolean rejectApplyForOffer(ApplicationForOffer myApplication) {
        boolean result = false;
        for (CompanionOfPet offer : offeringsOF) {
            if (myApplication.getIdOffer() == offer.getId()) {
                if (offer.getIdShelterIntPosition(0) == myApplication.getIdShelterApplying()) {
                    offer.setIdShelterIntPosition(0, 0);
                    offer.setNamesShelterIntPosition(null, 0);
                    myApplicationsForOffers.remove(myApplication);
                } else if (offer.getIdShelterIntPosition(1) == myApplication.getIdShelterApplying()) {
                    offer.setIdShelterIntPosition(0, 1);
                    offer.setNamesShelterIntPosition(null, 1);
                    myApplicationsForOffers.remove(myApplication);
                } else if (offer.getIdShelterIntPosition(2) == myApplication.getIdShelterApplying()) {
                    offer.setIdShelterIntPosition(0, 2);
                    offer.setNamesShelterIntPosition(null, 2);
                }
            }
        }
        if (myApplicationsForOffers.remove(myApplication)) {
            result = true;
        } else {
            result = false;
        }
        return result;

    }


}
