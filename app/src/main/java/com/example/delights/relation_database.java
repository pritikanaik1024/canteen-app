package com.example.delights;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {relation.class}, version = 1)
public abstract class relation_database extends RoomDatabase {
    public abstract relationDAO relationDao();
}