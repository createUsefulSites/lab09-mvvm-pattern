package ru.vuz.lab09dbnetwork.ui;

import java.net.URI;
import java.net.URISyntaxException;

public final class ImageUrlValidator {
    private ImageUrlValidator() {
    }

    public static boolean isValidImageUrl(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        try {
            URI uri = new URI(value.trim());
            String scheme = uri.getScheme();
            return uri.getHost() != null
                    && ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme));
        } catch (URISyntaxException exception) {
            return false;
        }
    }
}
