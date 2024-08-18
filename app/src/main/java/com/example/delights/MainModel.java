package com.example.delights;

public class MainModel {

    private String name;
    private String price;
    private String uri;

    // Default constructor required for Firebase
    public MainModel() {
    }

    // Parameterized constructor
    public MainModel(String name, String price, String uri) {
        this.name = name;
        this.price = price;
        this.uri = uri;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for price
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    // Getter and Setter for uri
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
