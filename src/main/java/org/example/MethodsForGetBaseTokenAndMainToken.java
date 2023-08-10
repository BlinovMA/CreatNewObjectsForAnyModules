package org.example;

import io.restassured.response.ValidatableResponse;
import utils.GetValueFromTurboPropertiesFile;

import static io.restassured.RestAssured.given;

public class MethodsForGetBaseTokenAndMainToken {

    public static String extractToken() {
        return responseWithToken.extract().path("access_token");
    }

    static String basicAuthToken = MethodsForGetBaseTokenAndMainToken.getBasicAuthToken();
    public static ValidatableResponse responseWithToken = MethodsForGetBaseTokenAndMainToken.getResponseWithToken(basicAuthToken);

    public static String getBasicAuthToken() {
        String response = given()
                .when()
                .get(GetValueFromTurboPropertiesFile.baseURL+GetValueFromTurboPropertiesFile.baseTokenEndPoint)
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

    public static ValidatableResponse getResponseWithToken(String basicAuthToken) {
        return given()
                .queryParam("grant_type", "password")
                .queryParam("username", GetValueFromTurboPropertiesFile.ovdUserLogin)
                .queryParam("password", GetValueFromTurboPropertiesFile.ovdPassword)
                .queryParam("client_id", "client-external")
                .queryParam("client_secret", "client-external")
                .header("Authorization", basicAuthToken)
                .header("Connection", "keep-alive")
                .when()
                .post(GetValueFromTurboPropertiesFile.baseURL+GetValueFromTurboPropertiesFile.mainTokenEndPoint)
                .then()
                .log().all()
                .log().ifError();
    }

}
