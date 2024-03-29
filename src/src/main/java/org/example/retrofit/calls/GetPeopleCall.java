package org.example.retrofit.calls;

import org.example.retrofit.mappings.responses.GetPeopleResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;

import java.util.Map;

public interface GetPeopleCall {
    @GET("people/")
    Call<GetPeopleResponse> getPeople(@Query("search") String name, @HeaderMap Map<String, Object> headers);

    @GET("people/")
    Call<GetPeopleResponse> getPeopleOnPage(@Query("page") String page, @HeaderMap Map<String, Object> headers);
}
