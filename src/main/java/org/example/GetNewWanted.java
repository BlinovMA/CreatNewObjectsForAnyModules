package org.example;

import io.restassured.response.ValidatableResponse;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetNewWanted  {
    @Test
    public void shouldCreateNewWanted() {
        // 1. получить токен Basic
        String basicAuthToken = getBasicAuthToken();
        Assert.assertNotNull(basicAuthToken);
        // 2. получить токен OAuth
        ValidatableResponse responseWithToken = getResponseWithToken(basicAuthToken);
        Assert.assertNotNull(responseWithToken);

        // 3. получить employee_id
        String token = responseWithToken.extract().path("access_token");
        Integer employeeId = responseWithToken.extract().path("employee_id");
        System.out.println(token + "\n++++++++++++++++++++++++++++++++++" + employeeId + "\n++++++++++++++++++++++++++++++++++");

        // 4. создать ориентировку
        ValidatableResponse WantedResponse = getWanted(token);
        WantedResponse.assertThat().statusCode(200);
    }


    public String getBasicAuthToken() {
        String response = given()
                .when()
                .get(" http://turbo4.apps.sodch.phoenixit.ru/js/config.js")
                .then()
                .extract().body().asString();
        System.out.println(response + "\n==========================");
        int indexStart = response.indexOf("Basic");
        int indexEnd = response.indexOf("==");
        System.out.println(indexStart + " " + indexEnd);
        String value = response.substring(indexStart, indexEnd + 2);
        System.out.println(value + "\n==========================");
        return value;
    }


    public ValidatableResponse getResponseWithToken( String basicAuthToken) {

        return given()
                .queryParam("grant_type", "password")
                .queryParam("username", "operalex")
                .queryParam("password", "operalex")
                .header("Authorization", getBasicAuthToken())
                .header("Connection", "keep-alive")
                .when()
                .post("http://turbo4.apps.sodch.phoenixit.ru/gateway/sso-service/oauth/token")
                .then()
                .log().all()
                .log().ifError();
    }


    public ValidatableResponse getStructureIdAndAccessToken(ValidatableResponse responseWithToken) {
        //   ValidatableResponse responseWithToken = getResponseWithToken();
        String token = responseWithToken.extract().path("access_token");
        Integer employeeId = responseWithToken.extract().path("employee_id");
        System.out.println(token + "\n++++++++++++++++++++++++++++++++++" + employeeId + "\n++++++++++++++++++++++++++++++++++");
        return given()
                .header("Authorization", "Bearer " + token)
                .header("Connection", "keep-alive")
                .when()
                .get("http://turbo4.apps.sodch.phoenixit.ru/gateway/dictionary-service/api/v1/employee/" + employeeId)
                .then()
                .log().all()
                .log().ifError();

    }

    public ValidatableResponse getWanted(String token) {


        ValidatableResponse responseGetDataWantedNumber =
                given()
                        .header("Authorization", "Bearer " + token)
                        .header("Connection", "keep-alive")
                        .when()
                        .get("http://turbo2.apps.sodch.phoenixit.ru/gateway/wanted-service/api/v1/wanted/number")
                        .then()
                        .log().all()
                        .log().ifError();


        Integer WantedNumber = responseGetDataWantedNumber
                .extract().path("data");
        System.out.println("data  " + WantedNumber);


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
                        .body(bodyDataWanted.toString())
                        .when()
                        .log().all()
                        .post("http://turbo2.apps.sodch.phoenixit.ru/gateway/wanted-service/api/v1/wanted")
                        .then()
                        .log().all()
                        .log().ifError();
    }
}


