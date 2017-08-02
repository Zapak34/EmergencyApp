package com.nightvisionmedia.emergencyapp.custom_models;

/**
 * Created by Omar (GAZAMAN) Myers on 8/1/2017.
 */

public class UserAccount {
    private int user_id;
    private String fname;
    private String lname;
    private String email;
    private String password;
    private String age;
    private String phone_number;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }



    public UserAccount(){

    }

    public UserAccount(int user_id, String fname, String lname, String email, String password, String age, String phone_number) {
        this.user_id = user_id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.age = age;
        this.phone_number = phone_number;
    }
}
