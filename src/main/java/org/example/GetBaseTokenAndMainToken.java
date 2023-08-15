package org.example;

import io.restassured.response.ValidatableResponse;
import utils.GetValueFromTurboPropertiesFile;

import static io.restassured.RestAssured.given;
import static utils.GetValueFromTurboPropertiesFile.baseTokenEndPoint;
import static utils.GetValueFromTurboPropertiesFile.baseURL;

public class GetBaseTokenAndMainToken {

    public static String extractToken() {
        return responseWithToken.extract().path("access_token");
    }

    static String basicAuthToken = GetBaseTokenAndMainToken.getBasicAuthToken();
    public static ValidatableResponse responseWithToken = GetBaseTokenAndMainToken.getResponseWithToken(basicAuthToken);

    public static String getBasicAuthToken() {
        String response = given()
                .when()
                .get(baseURL()+baseTokenEndPoint())
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
                .log().all()
                .post(baseURL()+GetValueFromTurboPropertiesFile.mainTokenEndPoint)
                .then()
                .log().all()
                .log().ifError();
    }

}
