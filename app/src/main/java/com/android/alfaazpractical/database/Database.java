package com.android.alfaazpractical.database;

import android.content.Context;

import androidx.room.Room;

public class Database {

    private Context context;
    private static Database database;

    //our app database object
    private AppDatabase appDatabase;

    private Database(Context context) {
        this.context = context;

        //creating the app database with Room database builder
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "Alfaj").build();
    }

    public static synchronized Database getInstance(Context ctx) {
        if (database == null) {
            database = new Database(ctx);
        }
        return database;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

}
