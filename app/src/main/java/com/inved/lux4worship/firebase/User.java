package com.inved.lux4worship.firebase;

import androidx.annotation.Nullable;

import java.util.List;

public class User {

    private String uid;
    private String firstname;
    private String lastname;
    @Nullable
    private String urlPicture;
    private List<String> instruments;
    private Boolean admin;
    private String churchId;
    private String churchName;
    private String churchAddress;

    public User() { }

    public User(String uid,
                String firstname,
                String lastname,
                @Nullable String urlPicture,
                List<String> instruments,
                Boolean admin,
                String churchId,
                String churchName,
                String churchAddress) {
        this.uid = uid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.urlPicture = urlPicture;
        this.instruments = instruments;
        this.admin = admin;
        this.churchId = churchId;
        this.churchName = churchName;
        this.churchAddress = churchAddress;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Nullable
    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(@Nullable String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public List<String> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<String> instruments) {
        this.instruments = instruments;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getChurchName() {
        return churchName;
    }

    public void setChurchName(String churchName) {
        this.churchName = churchName;
    }

    public String getChurchAddress() {
        return churchAddress;
    }

    public void setChurchAddress(String churchTown) {
        this.churchAddress = churchTown;
    }

}
