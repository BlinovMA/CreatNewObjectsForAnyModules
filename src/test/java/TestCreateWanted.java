import io.restassured.response.ValidatableResponse;
import org.example.WantedRequests;
import org.example.GetBaseTokenAndMainToken;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestCreateWanted {

    @Test
    public void shouldCreateNewWanted() {

        ValidatableResponse WantedResponse = WantedRequests.createWanted(GetBaseTokenAndMainToken.extractToken());
        WantedResponse.assertThat().statusCode(200);
    }
}
