package com.adarshaaman.askpro;

public class Slot {
    public int date ;
    public int month;
    public int starthour;
    public int startminute;


    public Slot  (){}



    public Slot(int date, int month, int starthour, int startminute) {
        this.date = date;
        this.month = month;
        this.starthour = starthour;
        this.startminute = startminute;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getStarthour() {
        return starthour;
    }

    public void setStarthour(int starthour) {
        this.starthour = starthour;
    }

    public int getStartminute() {
        return startminute;
    }

    public void setStartminute(int startminute) {
        this.startminute = startminute;
    }
}
