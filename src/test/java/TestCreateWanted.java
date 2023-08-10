import io.restassured.response.ValidatableResponse;
import org.example.MethodForGenerateNewWanted;
import org.example.MethodsForGetBaseTokenAndMainToken;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.example.MethodsForGetBaseTokenAndMainToken.responseWithToken;

public class TestCreateWanted {

    @Test
    public void shouldCreateNewWanted() {
        String token = responseWithToken.extract().path("access_token");
        ValidatableResponse WantedResponse = MethodForGenerateNewWanted.createWanted(MethodsForGetBaseTokenAndMainToken.extractToken());
        WantedResponse.assertThat().statusCode(200);
    }
}
