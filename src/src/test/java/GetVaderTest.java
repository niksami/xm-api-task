import org.example.retrofit.mappings.responses.GetPeopleResponse;
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
        assertThat("Endpoint works!", getResponse.code(), is(SC_OK));
    }

}
