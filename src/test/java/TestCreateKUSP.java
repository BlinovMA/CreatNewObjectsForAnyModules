import io.restassured.response.ValidatableResponse;
import org.example.MethodForGenerateNewKUSP;
import org.testng.annotations.Test;

import static org.example.MethodsForAuthorizationBaseTokenAndMainToken.extractToken;

public class TestCreateKUSP {
    @Test
    public void shouldCreateKusp() {
        ValidatableResponse kuspResponse = MethodForGenerateNewKUSP.createKusp(extractToken());
        kuspResponse.assertThat().statusCode(200);
    }
}
