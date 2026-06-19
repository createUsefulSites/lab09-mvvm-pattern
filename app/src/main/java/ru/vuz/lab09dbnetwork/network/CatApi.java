package ru.vuz.lab09dbnetwork.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.vuz.lab09dbnetwork.data.CatImage;

public interface CatApi {
    @GET("images/search")
    Call<List<CatImage>> getImages(
            @Query("limit") int limit,
            @Query("has_breeds") int hasBreeds
    );
}
