package org.example;

import io.restassured.response.ValidatableResponse;
import org.json.JSONObject;
import utils.GetValueFromTurboPropertiesFile;
import static io.restassured.RestAssured.given;
import static utils.GetValueFromTurboPropertiesFile.baseURL;
import static utils.ServiceRequestEndpoints.wantedServiceGateawayEndpoint;

public class WantedRequests {

    public static ValidatableResponse createWanted(String token) {

        ValidatableResponse responseGetDataWantedNumber =
                given()
                        .header("Authorization", "Bearer " + token)
                        .header("Connection", "keep-alive")
                        .when()
                        .log().all()
                        .get(baseURL()+wantedServiceGateawayEndpoint()+"wanted/number")
                        .then()
                        .log().all()
                        .log().ifError();

        Integer WantedNumber = responseGetDataWantedNumber.extract().path("data");
        //System.out.println("data  " + WantedNumber);

        JSONObject bodyDataWanted = new JSONObject();

        bodyDataWanted.put("number", WantedNumber);
        bodyDataWanted.put("addressId", "8845");
        bodyDataWanted.put("addressText", "г. Москва");
        bodyDataWanted.put("description", "Ищем");
        bodyDataWanted.put("expireAt", "2024-07-26T12:40:22.938");
        bodyDataWanted.put("status", "ACTIVE");
        bodyDataWanted.put("statusId", -23000);
        bodyDataWanted.put("structureId", -770001);

        return
                given()
                        .header("Authorization", "Bearer " + token)
                        .header("Connection", "keep-alive")
                        .header("Content-Type", "application/json")
                        .log().all()
                        .body(bodyDataWanted.toString())
                        .when()
                        .log().all()
                        .post(baseURL() +wantedServiceGateawayEndpoint()+"wanted")
                        .then()
                        .log().all()
                        .log().ifError();
    }
}


