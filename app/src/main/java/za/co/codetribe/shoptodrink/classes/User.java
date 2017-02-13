package za.co.codetribe.shoptodrink.classes;

import java.io.Serializable;

/**
 * Created by ProJava on 11/17/2016.
 */

public class User implements Serializable {

    private String userID;
    private String name;
    private String surname;
    private long IDNo;
    private String gender;
    private String cellphone;
    private String email;


    public User()
    {
        super();
    }


    public User(String userID, String name, String surname, long IDNo, String gender, String cellphone, String email) {
        this.userID = userID;
        this.name = name;
        this.surname = surname;
        this.IDNo = IDNo;
        this.gender = gender;
        this.cellphone = cellphone;
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public long getIDNo() {
        return IDNo;
    }

    public void setIDNo(long IDNo) {
        this.IDNo = IDNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
