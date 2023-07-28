package com.example.testing.models.PrototypePerson;

public class Customers extends PPrototype {

    private String email;

    public Customers(){

    }
    public Customers(Customers customer){
        super(customer);
        if (customer != null){
            this.email = customer.email;
        }
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public PPrototype clone(){
        return new Customers(this);
    }

}
