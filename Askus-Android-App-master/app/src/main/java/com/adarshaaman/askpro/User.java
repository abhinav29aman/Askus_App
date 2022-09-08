package com.adarshaaman.askpro;

public class User {
    private String phone ;
    private String Name ;
    private boolean isMentor;

    public User(String phone, String name, boolean isMentor) {
        this.phone = phone;
        Name = name;
        this.isMentor = isMentor;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isMentor() {
        return isMentor;
    }

    public void setMentor(boolean mentor) {
        isMentor = mentor;
    }
}
