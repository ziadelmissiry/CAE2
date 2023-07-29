package utl;

import android.app.Application;

public class BankiUsers extends Application {
    private String email;
    private String userId;

    private String pdf_name_create;
    private String pdf_url;

    private static BankiUsers instance;

    //following singltion design pattern


    public static BankiUsers getInstance(){
        if (instance == null){
            instance = new BankiUsers();
        }
            return instance;
    }

public BankiUsers(){
        //empty constructor
}

    public String getUserId() {
        return userId;
    }

    //getter email
public String getEmail(){
        return email;
}

//setter email
    public void setEmail(String email) {
        this.email = email;
    }

    //user id setter


    public void setUserId(String userId) {
        this.userId = userId;
    }
}
