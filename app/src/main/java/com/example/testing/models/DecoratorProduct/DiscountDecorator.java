package com.example.testing.models.DecoratorProduct;

public class DiscountDecorator extends ProductDecorator{

    private double discountPercentage;
    public DiscountDecorator(ProductInterfaceDecorator decoratedProduct, double discountPercentage){
        super(decoratedProduct);
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double getPrice() {
        double originalPrice = super.getPrice();
        double discountAmount = originalPrice * (discountPercentage/100);
        return originalPrice - discountAmount;
    }

    @Override
    public void setPrice(double price) {
        super.setInternalPrice(price);
    }

    @Override
    public String getName() {
        return super.getName() + " (" + (int) discountPercentage + "% Descuento)";
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

}
