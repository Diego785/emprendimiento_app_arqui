package com.example.testing.models.DecoratorProduct;

public abstract class ProductDecorator implements ProductInterfaceDecorator {
    protected ProductInterfaceDecorator decoratedProduct;

    public ProductDecorator(ProductInterfaceDecorator decoratedProduct) {
        this.decoratedProduct = decoratedProduct;
    }

    @Override
    public int getId() {
        return decoratedProduct.getId();
    }

    @Override
    public String getName() {
        return decoratedProduct.getName();
    }
    @Override
    public void setName(String name) {
        decoratedProduct.setName(name);
    }

    @Override
    public byte[] getPhoto() {
        return decoratedProduct.getPhoto();
    }

    @Override
    public double getPrice() {
        return decoratedProduct.getPrice();
    }
    protected void setInternalPrice(double price) {
        decoratedProduct.setPrice(price);
    }

    @Override
    public int getQuantity() {
        return decoratedProduct.getQuantity();
    }

    @Override
    public int getCategory_id() {
        return decoratedProduct.getCategory_id();
    }
}
