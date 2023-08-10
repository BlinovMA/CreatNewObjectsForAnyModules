import io.restassured.response.ValidatableResponse;
import org.example.MethodForGenerateNewKud;
import org.testng.annotations.Test;

import static org.example.MethodsForGetBaseTokenAndMainToken.*;

public class TestCreateKud {

    @Test
    public void shouldCreateKud() {

        ValidatableResponse responseOfGetEmployeeIDData = MethodForGenerateNewKud.getEmployeeIdWithAccessToken(responseWithToken);

        ValidatableResponse kudResponse = MethodForGenerateNewKud.createKud(extractToken(), responseOfGetEmployeeIDData);

        kudResponse.assertThat().statusCode(200);
    }

}
