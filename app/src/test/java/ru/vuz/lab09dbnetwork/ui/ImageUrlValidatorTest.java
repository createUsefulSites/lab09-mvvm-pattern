package ru.vuz.lab09dbnetwork.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ImageUrlValidatorTest {
    @Test
    public void acceptsHttpAndHttpsUrlsForPicasso() {
        assertTrue(ImageUrlValidator.isValidImageUrl("https://cdn.example/cat.jpg"));
        assertTrue(ImageUrlValidator.isValidImageUrl("http://cdn.example/cat.png"));
    }

    @Test
    public void rejectsInvalidValuesBeforePicassoLoad() {
        assertFalse(ImageUrlValidator.isValidImageUrl(""));
        assertFalse(ImageUrlValidator.isValidImageUrl("not a url"));
        assertFalse(ImageUrlValidator.isValidImageUrl("file:///tmp/cat.jpg"));
    }
}
