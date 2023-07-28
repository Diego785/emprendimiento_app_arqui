package com.example.testing.models.PrototypePerson;

public class Deliveries extends PPrototype {
    private int edad;
    private String sexo;

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public Deliveries() {
        super();
    }

    public Deliveries(Deliveries delivery) {
        super(delivery);
        if(delivery != null){
            this.edad = delivery.edad;
            this.sexo = delivery.sexo;
        }
    }


    @Override
    public PPrototype clone(){
        return new Deliveries(this);
    }


}
