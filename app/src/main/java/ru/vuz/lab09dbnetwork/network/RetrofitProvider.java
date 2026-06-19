package ru.vuz.lab09dbnetwork.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitProvider {
    public static final String BASE_URL = "https://api.thecatapi.com/v1/";

    private RetrofitProvider() {
    }

    public static CatApi createCatApi() {
        return createCatApi(BASE_URL);
    }

    public static CatApi createCatApi(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CatApi.class);
    }
}
