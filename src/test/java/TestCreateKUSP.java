import io.restassured.response.ValidatableResponse;
import org.example.KUSPRequests;
import org.testng.annotations.Test;

import static org.example.GetBaseTokenAndMainToken.extractToken;

public class TestCreateKUSP {
    @Test
    public void shouldCreateKusp() {
        ValidatableResponse kuspResponse = KUSPRequests.createKusp(extractToken());
        kuspResponse.assertThat().statusCode(200);
    }
}
