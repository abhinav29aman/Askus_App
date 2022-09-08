package com.adarshaaman.askpro;

public class Payment {
    private String transaction ;
    private int amount ;
    private String orderId ;
    private String accesstoken;

    public Payment(){}

    public Payment(String t ,int a , String o,String accesstoken){

        this.transaction =t;
        this.amount = a;
        this.orderId =o;
        this.accesstoken = accesstoken;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }
}
