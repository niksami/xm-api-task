import org.example.retrofit.mappings.responses.GetPeopleResponse;
import org.example.retrofit.mappings.responses.PeopleBean;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import static org.apache.http.HttpStatus.SC_OK;
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
            assertThat("Searching for Vader...", peopleBean.getName(), is("Darth Vader"));
        }
    }

}
