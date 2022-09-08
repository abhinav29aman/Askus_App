package com.adarshaaman.askpro;

public class Booking {
    int type ; //1 for audio , 2 for video
    int noOfSlots ; //how many continuous slots booked
    String begintime ;
    Mentor mentor;
    String userPhone ;
    int price;
    String endtime;

    public Booking(){}

    public Booking(int type, int noOfSlots, String begintime, Mentor mentor, String userPhone, int price,String endtime) {
        this.type = type;
        this.noOfSlots = noOfSlots;
        this.begintime = begintime;
        this.mentor = mentor;
        this.userPhone = userPhone;
        this.price = price;
        this.endtime= endtime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNoOfSlots() {
        return noOfSlots;
    }

    public void setNoOfSlots(int noOfSlots) {
        this.noOfSlots = noOfSlots;
    }

    public String getBegintime() {
        return begintime;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
