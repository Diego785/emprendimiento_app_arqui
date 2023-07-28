package com.example.testing.models.DecoratorProduct;

import com.example.testing.models.db.BuilderProduct.ProductInterfaceBuilder;

public class Product implements ProductInterfaceDecorator, ProductInterfaceBuilder {

    private int id;
    private String name;
    private byte[] photo;
    private double price;
    private int quantity;

    private int category_id;


    public Product(){
        super();
    }

    public Product(String name, byte[] photo, double price, int quantity, int category_id) {
        this.name = name;
        this.photo = photo;
        this.price = price;
        this.quantity = quantity;
        this.category_id = category_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }



    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int getCategory_id(){
        return category_id;
    }

    public void  setCategory_id(int category_id){
        this.category_id = category_id;
    }



    //BUILDER
    @Override
    public Product buildId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public Product buildName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public Product buildPhoto(byte[] photo) {
        this.photo = photo;
        return this;
    }

    @Override
    public Product buildPrice(double price) {
        this.price = price;
        return this;
    }

    @Override
    public Product buildQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public Product buildCategory_id(int category_id) {
        this.category_id = category_id;
        return this;
    }

    @Override
    public String buildGetName() {
        return name;
    }

    @Override
    public byte[] buildGetPhoto() {
        return photo;
    }

    @Override
    public double buildeGetPrice() {
        return price;
    }

    @Override
    public int buildGetQuantity() {
        return quantity;
    }

    public ProductInterfaceBuilder build(){
        return new Product(name, photo, price, quantity, category_id);
    }

}
