package org.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.sql.Connection;

import static io.restassured.RestAssured.given;
import static utils.GetValueFromTurboPropertiesFile.baseURL;
import static utils.ServiceRequestEndpoints.dictionaryServiceGateawayEndpoint;
import static utils.ServiceRequestEndpoints.kudServiceGateawayEndpoint;

public class KudRequests {

    public static ValidatableResponse getEmployeeIdWithAccessToken(ValidatableResponse responseWithToken) {

        String token = responseWithToken.extract().path("access_token");
        Integer employeeId = responseWithToken.extract().path("employee_id");
        System.out.println(token + "\n++++++++++employeeId = " + employeeId + "\n");
        return given()
                .header("Authorization", "Bearer " + token)
                .header("Connection", "keep-alive")
                .when()
                .get(baseURL() + dictionaryServiceGateawayEndpoint() + "employee/" + employeeId)
                .then()
                .log().all()
                .log().ifError();
    }

    public static ValidatableResponse createKud(String token, ValidatableResponse responseOfGetEmployeeIDData) {

        Integer structureId = responseOfGetEmployeeIDData.extract().path("data.structureId");
        ValidatableResponse responseGetDataKudNumber =
                given()
                        .header("Authorization", "Bearer " + token)
                        .header("Connection", "keep-alive")
                        .when()
                        .post(baseURL() + kudServiceGateawayEndpoint() + "kud/number?structureId=" + structureId)
                        .then()
                        .log().all()
                        .log().ifError();

        Integer kudNumber = responseGetDataKudNumber.extract().path("data");

        JSONObject bodyDataKud = new JSONObject();

        bodyDataKud.put("detainedFirstName", "Иванов");
        bodyDataKud.put("detainedMiddleName", "Иван");
        bodyDataKud.put("detainedLastName", "Иванович");
        bodyDataKud.put("detainedBirthPlace", "");
        bodyDataKud.put("kudNumber", kudNumber);
        bodyDataKud.put("registrationTimestamp", "2023-08-07T11:39:14.309");
        bodyDataKud.put("isArrested", false);
        bodyDataKud.put("isReceivedICR", false);
        bodyDataKud.put("detentionCircumstances", "");
        bodyDataKud.put("arrestProtocolNumber", "");
        bodyDataKud.put("isWantedForeign", false);
        bodyDataKud.put("detainedBirthdate", "2020-07-06");
        bodyDataKud.put("detainedNationalityId", -10001);
        bodyDataKud.put("reasonOfDetentionId", -25004);

        return
                given()
                        .header("Authorization", "Bearer " + token)
                        .header("Connection", "keep-alive")
                        .header("Content-Type", "application/json")
                        .header("Accept-Encoding", "gzip, deflate")
                        .spec(getRequestSpec())
                        .log().all()
                        .body(bodyDataKud.toString())
                        .when()
                        .log().all()
                        .post( "kud/")
                        .then()
                        .log().all()
                        .log().ifError();
    }

    private static RequestSpecification getRequestSpec(){
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseURL() + kudServiceGateawayEndpoint())
                .build();
        return requestSpec;
    }
}





