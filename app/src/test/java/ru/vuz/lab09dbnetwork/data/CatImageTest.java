package ru.vuz.lab09dbnetwork.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CatImageTest {
    @Test
    public void createManualMarksImageAsUserRecord() {
        CatImage image = CatImage.createManual(" https://cdn.example/manual.jpg ", 1000L);

        assertTrue(image.isAddedByUser());
        assertEquals("https://cdn.example/manual.jpg", image.getImageUrl());
        assertEquals("manual-1000", image.getRemoteId());
        assertEquals(1000L, image.getCachedAt());
    }
}
