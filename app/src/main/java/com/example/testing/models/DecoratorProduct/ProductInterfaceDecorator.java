package com.example.testing.models.DecoratorProduct;

public interface ProductInterfaceDecorator {
    int getId();
    String getName();
    void setName(String name);
    byte[] getPhoto();
    double getPrice();
    void setPrice(double price);
    int getQuantity();
    int getCategory_id();
}
