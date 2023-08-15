import io.restassured.response.ValidatableResponse;
import org.example.KudRequests;
import org.testng.annotations.Test;

import static org.example.GetBaseTokenAndMainToken.*;

public class TestCreateKud {

    @Test
    public void shouldCreateKud() {

        ValidatableResponse responseOfGetEmployeeIDData = KudRequests.getEmployeeIdWithAccessToken(responseWithToken);

        ValidatableResponse kudResponse = KudRequests.createKud(extractToken(), responseOfGetEmployeeIDData);

        kudResponse.assertThat().statusCode(200);
    }

}
