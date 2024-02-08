package org.example.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

public class RetrofitBuilder {
    private static final String BASE_URL = System.getProperty("base_url", "https://swapi.dev/api/");
    private static final Integer TIMEOUT = Integer.valueOf(System.getProperty("timeout", "60"));

    private final Retrofit retrofit;

    public RetrofitBuilder() {
        this(HttpLoggingInterceptor.Level.BODY, BASE_URL);
    }

    public RetrofitBuilder(HttpLoggingInterceptor.Level level, String baseURL) {
        retrofit = buildRetrofit(buildClient(level), baseURL);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    private OkHttpClient buildClient(HttpLoggingInterceptor.Level level) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(level);

        return new OkHttpClient.Builder()
                .addInterceptor(logging.setLevel(level))
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    private Retrofit buildRetrofit(OkHttpClient client, String baseURL) {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();
    }

}
