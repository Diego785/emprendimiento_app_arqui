package com.example.testing.models.DecoratorProduct;

public class OriginalDecorator extends ProductDecorator {
    public OriginalDecorator(ProductInterfaceDecorator decoratedProduct) {
        super(decoratedProduct);
    }

    @Override
    public double getPrice() {
        return super.getPrice();
    }

    @Override
    public void setPrice(double price) {
        super.setInternalPrice(price);
    }

    @Override
    public String getName() {
        return super.getName() + " (Original)";
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }
}