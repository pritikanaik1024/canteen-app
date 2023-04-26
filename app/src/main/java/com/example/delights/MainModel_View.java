package com.example.delights;

public class MainModel_View {

    String name,Quantity ;

    MainModel_View()
    {


    }


    public String getName() {
        return name;
    }

    public String getQuantity() {
        return Quantity;
    }

    public MainModel_View(String name, String quantity) {
        this.name = name;
        Quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }















}
