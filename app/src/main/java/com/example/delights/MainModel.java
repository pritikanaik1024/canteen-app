package com.example.delights;

public class MainModel {

    String name,price,uri;

    MainModel()
    {


    }

    public MainModel(String name, String price, String uri) {
        this.name = name;
        this.price = price;
        this.uri = uri;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getUri() {
        return uri;
    }


}
