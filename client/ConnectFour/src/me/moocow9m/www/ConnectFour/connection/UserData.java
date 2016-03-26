package me.moocow9m.www.ConnectFour.connection;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Moocow9m on 3/25/2016.
 */
public class UserData implements Serializable {
    private String Username = "";
    private String Password = "";
    private double Version = 0.0;
    private ArrayList<Boolean> BoardSetup = new ArrayList<>();

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
   public void setVersion(double Version) {
        this.Version = Version;
    }
   public double getVersion() {
        return Version;
    }
}
