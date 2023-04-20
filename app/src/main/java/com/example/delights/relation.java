package com.example.delights;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class relation {
    @PrimaryKey (autoGenerate = true)
    public int fid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "price")
    public String price;

    @ColumnInfo(name = "quantity")
    public String quantity;

}
