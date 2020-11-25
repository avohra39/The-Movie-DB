package com.android.alfaazpractical.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.android.alfaazpractical.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    List<Movie> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Movie> movies);

}
