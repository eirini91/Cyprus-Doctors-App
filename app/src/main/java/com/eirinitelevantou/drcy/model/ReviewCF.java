package com.eirinitelevantou.drcy.model;

import com.contentful.vault.ContentType;
import com.contentful.vault.Field;
import com.contentful.vault.Resource;

/**
 * Created by eirini.televantou on 07/12/2017.
 */
@ContentType("review")

public class ReviewCF extends Resource {

    @Field
    String id;
    @Field
    String review;
    @Field
    String userId;
    @Field
    Boolean isAnonymised;
    @Field
    Double rating;
    @Field
    Integer doctorId;
    @Field
    String userImageUrl;
    @Field
    String userName;

    @Field
    Boolean hide;

    public ReviewCF() {
    }

}
