package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class MethodsForAuthorizationBaseTokenAndMainToken {

    public static String extractToken() {
        return responseWithToken.extract().path("access_token");
    }

    static String basicAuthToken = MethodsForAuthorizationBaseTokenAndMainToken.getBasicAuthToken();
    static ValidatableResponse responseWithToken = MethodsForAuthorizationBaseTokenAndMainToken.getResponseWithToken(basicAuthToken);

    public static String getBasicAuthToken() {
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

    public static ValidatableResponse getResponseWithToken(String basicAuthToken) {
        return given()
                .queryParam("grant_type", "password")
                .queryParam("username", "operalex")
                .queryParam("password", "operalex")
                .queryParam("client_id", "client-external")
                .queryParam("client_secret", "client-external")
                .header("Authorization", basicAuthToken)
                .header("Connection", "keep-alive")
                .when()
                .post("http://turbo4.apps.sodch.phoenixit.ru/gateway/sso-service/oauth/token")
                .then()
                .log().all()
                .log().ifError();
    }

}
