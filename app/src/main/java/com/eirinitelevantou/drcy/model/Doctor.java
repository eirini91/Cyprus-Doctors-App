package com.eirinitelevantou.drcy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eirini.televantou on 07/12/2017.
 */

public class Doctor {

    /**
     * Id : 2
     * Name : Δρ. ΒΑΣΙΛΕΙΟΥ ΣΥΛΒΙΑ
     * Speciality : 9
     * Sex : 1
     * Sector : 0
     * Uni : ΡΩΣΣΙΑ
     * Tel : 22590520
     * Languages : 	Greek, English, Swedish, Russian
     * Address : 28ΗΣ ΟΚΤΩΒΡΙΟΥ 47, 3ΟΣ ΟΡΟΦΟΣ, ΓΡ. 302, 2414 Λευκωσία
     * City : 0
     * Website : https://www.iator.com.cy/
     */

    @SerializedName("Id")
    private int Id;
    @SerializedName("Name")
    private String Name;
    @SerializedName("Speciality")
    private String Speciality;
    @SerializedName("Sex")
    private int Sex;
    @SerializedName("Sector")
    private int Sector;
    @SerializedName("Uni")
    private String Uni;
    @SerializedName("Tel")
    private String Tel;
    @SerializedName("Languages")
    private String Languages;
    @SerializedName("Address")
    private String Address;
    @SerializedName("City")
    private int City;
    @SerializedName("Website")
    private String Website;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public void setSpeciality(String Speciality) {
        this.Speciality = Speciality;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int Sex) {
        this.Sex = Sex;
    }

    public int getSector() {
        return Sector;
    }

    public void setSector(int Sector) {
        this.Sector = Sector;
    }

    public String getUni() {
        return Uni;
    }

    public void setUni(String Uni) {
        this.Uni = Uni;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String Tel) {
        this.Tel = Tel;
    }

    public String getLanguages() {
        return Languages;
    }

    public void setLanguages(String Languages) {
        this.Languages = Languages;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public int getCity() {
        return City;
    }

    public void setCity(int City) {
        this.City = City;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String Website) {
        this.Website = Website;
    }
}
