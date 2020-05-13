package com.petpal.petpaltravel.model.Persistance;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.petpal.petpaltravel.model.CompanionForPet;
import com.petpal.petpaltravel.model.CompanionOfPet;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.List;


public class PPTRestClient {
    //URL base de la API de Marvel. Totes les peticions parteixen d'aquí.
    private String baseUrl = "";
    //Clau pública (obtinguda mitjançant registre a la web de Marvel)
    private String publicKey;
    //Clau privada (obtinguda mitjançant registre a la web de Marvel)
    private String privateKey;
    private final String LIST_ALL_OFFERS= "listOffers";
    private final String LIST_ALL_DEMANDS= "listDemands";
    private final String USER_INFO= "userInfo";

    //URL de la API que permet obtenir la llista de personatges.
    //Es forma partint de la base donat que la URL és http://gateway.marvel.com/v1/public/characters

    public PPTRestClient(){
    }


    public List<CompanionOfPet> listAllOffers() throws IOException {
        //Generem l'objecte URL que fa servir HttpURLConnection
        URL url = new URL(baseUrl + "?" + LIST_ALL_OFFERS);
        //L'objecte HttpUrlConnection ens permet manipular una connexió HTTP.
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        //Connectem amb el servidor
        con.connect();

        String response = getResponseBody(con);
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        ListOffersOF d = new Gson().fromJson(jsonObject.get("data"), ListOffersOF.class);
        return d.oferings;
    }

    public List<CompanionForPet> listAllDemands() throws IOException {
        //Generem l'objecte URL que fa servir HttpURLConnection
        URL url = new URL(baseUrl + "?" + LIST_ALL_DEMANDS);
        //L'objecte HttpUrlConnection ens permet manipular una connexió HTTP.
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        //Connectem amb el servidor
        con.connect();

        String response = getResponseBody(con);
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        ListDemandsFOR d = new Gson().fromJson(jsonObject.get("data"), ListDemandsFOR.class);
        return d.demands;
    }


    /*
     * Funció auxiliar per printar el resultat d'una petició
     *
     */
    private String getResponseBody(HttpURLConnection con) throws IOException {
        BufferedReader br;

        if (con.getResponseCode() >= 400) {
            //Si el codi de resposta és superior a 400 s'ha produit un error i cal llegir
            //el missatge d'ErrorStream().
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        } else {
            //Si el codi és inferior a 400 llavors obtenim una resposta correcte del servidor.
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();
        return sb.toString();
    }
}
