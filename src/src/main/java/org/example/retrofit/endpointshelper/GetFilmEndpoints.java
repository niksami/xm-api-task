package org.example.retrofit.endpointshelper;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.example.retrofit.RetrofitBuilder;
import org.example.retrofit.calls.GetFilmCall;
import org.example.retrofit.mappings.responses.FilmBean;
import retrofit2.Response;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

@NoArgsConstructor(access = PRIVATE)
public class GetFilmEndpoints {
    private static final GetFilmCall GET_FILM_CALLS = new RetrofitBuilder().getRetrofit().create(GetFilmCall.class);

    @SneakyThrows
    public static Response<FilmBean> getFilmResponseById(String url) {

        Map<String, Object> headers = Map.of(CONTENT_TYPE, APPLICATION_JSON);

        return GET_FILM_CALLS.getFilmById(getFilmIdFromURL(url), headers).execute();
    }

    private static String getFilmIdFromURL(String url) {
        String regex = ".*/(\\d+)/?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        String filmNumber = null;
        if (matcher.find()) {
            filmNumber = matcher.group(1); // Extract the digits
        }
        return filmNumber;
    }
}
