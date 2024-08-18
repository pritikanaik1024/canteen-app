package com.example.delights;

public class MainModel_View {

    private String name;
    private String quantity;

    // Default constructor required for Firebase
    public MainModel_View() {
    }

    // Parameterized constructor
    public MainModel_View(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for quantity
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
