package org.example.retrofit.endpointshelper;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.example.retrofit.RetrofitBuilder;
import org.example.retrofit.calls.GetPeopleCall;
import org.example.retrofit.mappings.responses.GetPeopleResponse;
import retrofit2.Response;

import java.util.Map;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

@NoArgsConstructor(access = PRIVATE)

public class GetPeopleEndpoints {

    private static final GetPeopleCall GET_PEOPLE_CALLS = new RetrofitBuilder().getRetrofit().create(GetPeopleCall.class);

    @SneakyThrows
    public static Response<GetPeopleResponse> getPeopleResponse(String name) {

        Map<String, Object> headers = Map.of(CONTENT_TYPE, APPLICATION_JSON);

        return GET_PEOPLE_CALLS.getPeople(name, headers).execute();
    }

}
