package org.example.retrofit.calls;

import org.example.retrofit.mappings.responses.FilmBean;
import org.example.retrofit.mappings.responses.GetFilmResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.Map;

public interface GetFilmCall {
    @GET("films/")
    Call<GetFilmResponse> getFilms(@Query("search") String name, @HeaderMap Map<String, Object> headers);

    @GET("films/{id}")
    Call<FilmBean> getFilmById(@Path("id") String id, @HeaderMap Map<String, Object> headers);
}
