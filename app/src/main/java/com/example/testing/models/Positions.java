package com.example.testing.models;

public class Positions {
    private int id;
    private String name;
    private byte[] photo;
    private String url;

    private int customer_id;


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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public int getCustomer_id(){
        return customer_id;
    }

    public void  setCustomer_id(int customer_id){
        this.customer_id = customer_id;
    }
}
