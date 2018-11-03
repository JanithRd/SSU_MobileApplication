package srilankainstituteofinformationtechnology.smartsecureduniversity;

/**
 * Created by Gihan Wijesekara on 9/30/2018.
 */

public class Globals{
    private static Globals instance;

    // Global variable
    private String userEmail;

    // Restrict the constructor from being instantiated
    private Globals(){}

    public void setData(String d){
        this.userEmail=d;
    }
    public String getData(){
        return this.userEmail;
    }

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }
}