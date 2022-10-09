package com.example.pelemele;


import java.util.ArrayList;

public class Contact {
    protected String nom;
    protected String num;

    public Contact(String nom, String num) {
        this.nom = nom;
        this.num = num;
    }

    public String getNom() {
        return nom;
    }

    public String getNum() {
        return num;
    }


}
