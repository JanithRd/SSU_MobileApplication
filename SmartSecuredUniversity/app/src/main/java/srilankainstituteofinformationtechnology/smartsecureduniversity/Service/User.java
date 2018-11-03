package srilankainstituteofinformationtechnology.smartsecureduniversity.Service;

/**
 * Created by Janith Rd on 8/11/2018.
 */

public class User {

    public String eContact1;
    public String eContact2;
    public String eContact3;
    public String refreshToken;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String eContact1, String eContact2, String eContact3, String refreshToken) {
        this.eContact1 = eContact1;
        this.eContact2 = eContact2;
        this.eContact3= eContact3;
        this.refreshToken = refreshToken;
    }
}
