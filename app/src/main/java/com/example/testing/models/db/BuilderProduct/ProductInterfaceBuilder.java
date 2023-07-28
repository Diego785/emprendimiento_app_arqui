package com.example.testing.models.db.BuilderProduct;

import com.example.testing.models.DecoratorProduct.Product;

public interface ProductInterfaceBuilder {
    Product buildId(int id);
    Product buildName(String name);
    Product buildPhoto(byte[] photo);
    Product buildPrice(double price);
    Product buildQuantity(int quantity);
    Product buildCategory_id(int category_id);
    String buildGetName();
    byte[] buildGetPhoto();
    double buildeGetPrice();
    int buildGetQuantity();
}
