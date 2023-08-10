import io.restassured.response.ValidatableResponse;
import org.example.MethodForGenerateNewWanted;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.example.MethodsForAuthorizationBaseTokenAndMainToken.extractToken;

public class TestCreateWanted {

    @Test
    public void shouldCreateNewWanted() {

        ValidatableResponse WantedResponse = MethodForGenerateNewWanted.createWanted(extractToken());
        WantedResponse.assertThat().statusCode(200);
    }
}
