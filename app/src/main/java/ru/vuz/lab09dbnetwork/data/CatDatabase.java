package ru.vuz.lab09dbnetwork.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CatImage.class}, version = 1, exportSchema = false)
public abstract class CatDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "lab09_cat_cache.db";
    private static volatile CatDatabase instance;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(2);

    public abstract CatImageDao catImageDao();

    public static CatDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (CatDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    CatDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
