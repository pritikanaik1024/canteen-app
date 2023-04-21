package com.example.delights;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class relation {
    @PrimaryKey (autoGenerate = true)
    public int fid;

    @ColumnInfo(name = "food_name")
    public String food_name;

    @ColumnInfo(name = "Total_price")
    public String sum;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public relation(String food_name, String sum, String quantity) {
        this.fid = fid;
        this.food_name = food_name;
        this.sum = sum;
        this.quantity = quantity;
    }

    @ColumnInfo(name = "quantity")
    public String quantity;

}
