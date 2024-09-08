package com.example.project_lthdt.model;

public class GetDataUsers {
    int id;
    String name;
    String email;
    String mobile;
    String address;
    String isAdmin;
    String gender;
    String isStore;


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIsStore() {
        return isStore;
    }

    public void setIsStore(String isStore) {
        this.isStore = isStore;
    }

    public GetDataUsers(int id, String name, String email, String mobile, String address, String isAdmin, String gender, String isStore) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.isAdmin = isAdmin;
        this.gender = gender;
        this.isStore = isStore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }
}
