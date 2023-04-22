package com.example.delights;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface relationDAO {


    @Insert
    void insertall(relation...relation);


    @Query("SELECT EXISTS(SELECT * FROM relation WHERE fid = :foodid)")
    Boolean is_exist(int foodid);


    @Query("DELETE FROM relation")
    void deleteall();

    @Query("SELECT * FROM relation")
    List<relation> getAll_relations();

    @Query("DELETE FROM relation WHERE fid = :id")
    void deletebyId (int id);

    @Delete
    void delete(relation...relation);
}
