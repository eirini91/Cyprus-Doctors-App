package com.eirinitelevantou.drcy.util;

import android.content.Context;

import com.eirinitelevantou.drcy.model.Doctor;
import com.eirinitelevantou.drcy.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by eirini.televantou on 14/12/2017.
 */

public class ProjectUtils {

    public static String capitalize(String capString) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capitalizeGr(capMatcher.appendTail(capBuffer).toString());
    }

    public static String capitalizeGr(String capString) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([α-ω])([ά-ώ]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

//        capMatcher = Pattern.compile("([ά-ώ])([ά-ώ]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
//        while (capMatcher.find()){
//            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
//        }

        return capMatcher.appendTail(capBuffer).toString();
    }

    public static User getCurrentUser(Context context) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        User userToReturn = new User();
//            switch (PrefsHelper.getLoggedInType()){
//                case PrefsHelper.AUTH_TYPE_FIREBASE:{
        FirebaseUser user = firebaseAuth.getCurrentUser();
       if(user!=null) {
           userToReturn.setUserName(user.getDisplayName());
           userToReturn.setEmail(user.getEmail());
           userToReturn.setUserId(user.getUid());
           if (user.getPhotoUrl() != null)
               userToReturn.setImageUrl(user.getPhotoUrl().toString());
//                    break;
//                }
//                case PrefsHelper.AUTH_TYPE_GOOGLE:{
//                    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);
//                    userToReturn.setUserName(acct.getDisplayName());
//                    userToReturn.setEmail(acct.getEmail());
//                    userToReturn.setUserId(acct.getId());
//                    if(acct.getPhotoUrl()!=null)
//                    userToReturn.setImageUrl(acct.getPhotoUrl().toString());
//                    break;
//                }
//                case PrefsHelper.AUTH_TYPE_FACEBOOK:{
//                    FirebaseUser user = firebaseAuth.getCurrentUser();
//                    userToReturn.setUserName(user.getDisplayName());
//                    userToReturn.setEmail(user.getEmail());
//                    userToReturn.setUserId(user.getUid());
//                    if(user.getPhotoUrl()!=null)
//                        userToReturn.setImageUrl(user.getPhotoUrl().toString());
//                    break;
//                }
//            }
       }
        return userToReturn;
    }

    public static ArrayList<Doctor> getTop20(ArrayList<Doctor> allDoctors, Context context) {
        ArrayList<Doctor> ratedDoctors = new ArrayList<>();
        for (Doctor doctor : allDoctors) {
            if (!doctor.getRating().isNaN()) {
                ratedDoctors.add(doctor);
            }
        }
        Collections.sort(ratedDoctors, new Comparator<Doctor>() {
            public int compare(Doctor obj1, Doctor obj2) {
                // ## Ascending order
//                return obj1.getRating().compareToIgnoreCase(obj2.getRating()); // To compare string values
                return Double.compare(obj2.getRating(), obj1.getRating());

                // ## Descending order
                // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                // return Integer.valueOf(obj2.empId).compareTo(obj1.empId); // To compare integer values
            }
        });

        if (ratedDoctors.size() > 21) {
            return new ArrayList<Doctor>(ratedDoctors.subList(0, 20));
        } else {
            return ratedDoctors;
        }
    }
}
