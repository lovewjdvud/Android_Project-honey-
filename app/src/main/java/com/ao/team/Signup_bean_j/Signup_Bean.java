package com.ao.team.Signup_bean_j;

public class Signup_Bean {

 private String id;
 private String pw;
 private String name;
 private String phone;
 private String email;

    public Signup_Bean(String id, String pw, String name, String phone, String email) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Signup_Bean(String id) {
        this.id = id;
    }

    public Signup_Bean(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    public Signup_Bean(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Signup_Bean(String id, String pw, String name, String phone) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

