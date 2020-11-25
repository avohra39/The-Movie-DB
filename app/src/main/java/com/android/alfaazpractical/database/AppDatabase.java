package com.android.alfaazpractical.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.android.alfaazpractical.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}
