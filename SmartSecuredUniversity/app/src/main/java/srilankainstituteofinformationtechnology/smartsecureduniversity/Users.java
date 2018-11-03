package srilankainstituteofinformationtechnology.smartsecureduniversity;

/**
 * Created by Gihan Wijesekara on 6/22/2018.
 */

public class Users {

    public String name,profileImageUrl, userId, twitterId;

    public Users(){

    }

    public String getprofileImageUrl() { return profileImageUrl;  }

    public String getName() {
        return name;
    }

    public String getUserId() {return userId;}

    public String getTwitterId() {return twitterId;}



    public Users(String name,String profileImageUrl, String userId, String twitterId ) {
        this.name = name;
        this.profileImageUrl=profileImageUrl;
        this.userId=userId;
        this.twitterId=twitterId;

    }
}