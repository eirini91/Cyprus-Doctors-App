package com.eirinitelevantou.drcy.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by eirini.televantou on 07/12/2017.
 */

public class Review extends RealmObject {

    @PrimaryKey
    private String Id;
    private String Review;
    private String UserId;
    private Boolean IsAnonymised;
    private Double Rating;
    private Integer DoctorId;
    private String UserImageUrl;
    private String UserName;
    private Boolean hide = false;

    public Review() {
    }

    public Review(ReviewCF reviewCF) {
        this.Id = reviewCF.id;
        this.Review = reviewCF.review;
        this.UserId = reviewCF.userId;
        this.IsAnonymised = reviewCF.isAnonymised;
        this.Rating = reviewCF.rating;
        this.DoctorId = reviewCF.doctorId;
        this.UserName = reviewCF.userName;
        this.UserImageUrl = reviewCF.userImageUrl;
        this.hide = reviewCF.hide;

    }

    public Review(String id, String review, String userId, Boolean isAnonymised, Double rating, Integer doctorId, String userName, String userImageUrl) {
        Id = id;
        Review = review;
        UserId = userId;
        IsAnonymised = isAnonymised;
        Rating = rating;
        DoctorId = doctorId;
        UserName = userName;
        UserImageUrl = userImageUrl;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public Boolean getAnonymised() {
        return IsAnonymised;
    }

    public void setAnonymised(Boolean anonymised) {
        IsAnonymised = anonymised;
    }

    public Double getRating() {
        return Rating;
    }

    public void setRating(Double rating) {
        Rating = rating;
    }

    public Integer getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(Integer doctorId) {
        DoctorId = doctorId;
    }

    public String getUserImageUrl() {
        return UserImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        UserImageUrl = userImageUrl;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public Boolean getHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }
}
