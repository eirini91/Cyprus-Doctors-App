package com.eirinitelevantou.drcy.model;

import android.content.Context;

import com.eirinitelevantou.drcy.DrApp;
import com.eirinitelevantou.drcy.R;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by eirini.televantou on 07/12/2017.
 */

public class Doctor extends RealmObject {

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

    @PrimaryKey
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

    private RealmList<Review> reviews;
    private Double rating = 0.0;
    private Boolean isFavourite = false;

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

    public List<Integer> getSpecialities() {

        List<Integer> list = new ArrayList<Integer>();
        String specialties = Speciality.replaceAll("\\s+", "");
        specialties = specialties + ",";
        for (int i = 0, j, n = specialties.length(); i < n; i = j + 1) {
            j = specialties.indexOf(",", i);
            try {
                list.add(Integer.parseInt(specialties.substring(i, j).trim()));

            } catch (NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }
        }

        return list;
    }

    public String getCommaSeparatedSpecialties() {
        String specialties = "";
        for (Specialty specialty1 : DrApp.getInstance().getSpecialtyArrayList()) {
            if (getSpecialities().contains(specialty1.getId())) {
                if (specialties.length() > 0) {
                    specialties += ", ";
                }
                specialties += specialty1.getName();

            }
        }
        return specialties;
    }

    public String getCityString(Context context) {
        switch (getCity()) {
            case 0: {
                return context.getString(R.string.nicosia_cyprus);
            }
            case 1: {
                return context.getString(R.string.limassol_cuprus);
            }
            case 2: {
                return context.getString(R.string.larnaca_cyprus);
            }
            case 3: {
                return context.getString(R.string.paphos_cyprus);
            }
            case 4: {
                return context.getString(R.string.famagusta_cyprus);
            }

        }
        return "";

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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    public RealmList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(RealmList<Review> reviews) {
        this.reviews = reviews;
    }
}
