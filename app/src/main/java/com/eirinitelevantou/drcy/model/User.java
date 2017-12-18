package com.eirinitelevantou.drcy.model;

import io.realm.RealmObject;

/**
 * Created by eirini.televantou on 07/12/2017.
 */

public class User extends RealmObject {

    private String UserId;
    private String ImageUrl;
    private String UserName;
    private String Email;

    public User() {
    }

    public User(String userId, String imageUrl, String userName) {
        UserId = userId;
        ImageUrl = imageUrl;
        UserName = userName;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
