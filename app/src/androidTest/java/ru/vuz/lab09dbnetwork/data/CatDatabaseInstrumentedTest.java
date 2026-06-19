package ru.vuz.lab09dbnetwork.data;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CatDatabaseInstrumentedTest {
    private CatDatabase database;
    private CatImageDao dao;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, CatDatabase.class)
                .allowMainThreadQueries()
                .build();
        dao = database.catImageDao();
    }

    @After
    public void tearDown() {
        database.close();
    }

    @Test
    public void roomStoresManualImage() {
        CatImage image = CatImage.createManual("https://cdn.example/cat.jpg", 1000L);

        dao.insert(image);

        assertEquals(1, dao.countImages());
        assertEquals("https://cdn.example/cat.jpg", dao.findByRemoteId("manual-1000").getImageUrl());
    }
}
