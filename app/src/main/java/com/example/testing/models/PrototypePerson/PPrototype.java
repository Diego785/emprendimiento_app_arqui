package com.example.testing.models.PrototypePerson;

public abstract class PPrototype {
    private int id;
    private  String name;
    private String telephone;



    public PPrototype(){

    }

    public PPrototype(PPrototype prototype){
        if(prototype != null){
            this.name = prototype.name;
            this.telephone = prototype.telephone;
        }
    }

    public abstract PPrototype clone();

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
