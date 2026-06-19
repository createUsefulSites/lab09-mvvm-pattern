package ru.vuz.lab09dbnetwork.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Response;
import ru.vuz.lab09dbnetwork.data.CatImage;

public class CatApiTest {
    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void retrofitParsesCatApiResponse() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("[{\"id\":\"abc\",\"url\":\"https://cdn.example/cat.jpg\",\"width\":640,\"height\":480}]"));
        CatApi api = RetrofitProvider.createCatApi(server.url("/v1/").toString());

        Response<List<CatImage>> response = api.getImages(1, 1).execute();

        assertTrue(response.isSuccessful());
        assertEquals("abc", response.body().get(0).getRemoteId());
        assertEquals("https://cdn.example/cat.jpg", response.body().get(0).getImageUrl());
        assertEquals(640, response.body().get(0).getWidth());
        RecordedRequest request = server.takeRequest();
        assertEquals("/v1/images/search?limit=1&has_breeds=1", request.getPath());
    }
}
