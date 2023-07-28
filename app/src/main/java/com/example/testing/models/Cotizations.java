package com.example.testing.models;

import java.util.ArrayList;

public class Cotizations {

    private int id;
    private String name;
    private double price;
    private int quantity;
    private byte[] muestra;

    public byte[] getMuestra() {
        return muestra;
    }

    public void setMuestra(byte[] muestra) {
        this.muestra = muestra;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cotizations(String name, double price, int quantity, byte[] sample) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.muestra = sample;
    }

}
