import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.example.retrofit.mappings.responses.FilmBean;
import org.example.retrofit.mappings.responses.GetPeopleResponse;
import org.example.retrofit.mappings.responses.PeopleBean;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import static org.apache.http.HttpStatus.SC_OK;
import static org.example.retrofit.endpointshelper.GetFilmEndpoints.getFilmResponseById;
import static org.example.retrofit.endpointshelper.GetPeopleEndpoints.getPeopleResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GetVaderTest {
    @Test
    public void getVaderTest() {
        Response<GetPeopleResponse> getResponse = getPeopleResponse("Vader");
        assert getResponse.body() != null;
        PeopleBean[] peopleFound = getResponse.body().getResults();
        assertThat("Endpoint works!", getResponse.code(), is(SC_OK));
        for (PeopleBean peopleBean : peopleFound) {
            FilmBean filmBean = filmWithLeastPlanets(peopleBean);
            System.out.println("Film with least planets (" + filmBean.getPlanets().size() + ") is: " + filmBean.getTitle());
            System.out.println(isStarshipInFilm(peopleBean, filmBean));
        }
    }

    @Test
    public void testPeopleSchema() throws IOException {
        URL schemaUrl = GetVaderTest.class.getResource("/people_response_schema.json");
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
        JsonNode schemaNode = new ObjectMapper().readTree(schemaUrl);
        JsonSchema schema = schemaFactory.getSchema(schemaNode);

        URL responseUrl = new URL("https://swapi.dev/api/people/");
        JsonNode responseNode = new ObjectMapper().readTree(responseUrl);

        Set<ValidationMessage> validationResult = schema.validate(responseNode);

        if (validationResult.isEmpty()) {
            System.out.println("Response is valid according to the schema.");
        } else {
            System.out.println("Response is invalid according to the schema. Errors:");
            validationResult.forEach(System.out::println);
        }
    }

    private FilmBean filmWithLeastPlanets(PeopleBean peopleBean) {
        FilmBean filmWithLeastPlanets = null;
        int minPlanetCount = Integer.MAX_VALUE;
        for (String film : peopleBean.getFilms()) {
            Response<FilmBean> getFilmResponse = getFilmResponseById(film);
            if (film != null) {
                assert getFilmResponse.body() != null;
                if (getFilmResponse.body().getPlanets().size() < minPlanetCount) {
                    filmWithLeastPlanets = getFilmResponse.body();
                    minPlanetCount = getFilmResponse.body().getPlanets().size();
                }
            }
        }
        return filmWithLeastPlanets;
    }

    private String isStarshipInFilm(PeopleBean peopleBean, FilmBean filmBean) {
        for (String starship : peopleBean.getStarships()) {
            for (String starshipFilm : filmBean.getStarships()) {
                if (starship.equals(starshipFilm)) return peopleBean.getName() + " starship is present in that film!";
            }
        }
        return peopleBean.getName() + " starship is not present in that film!";
    }

}
