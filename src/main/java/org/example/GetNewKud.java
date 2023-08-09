package org.example;

import io.restassured.response.ValidatableResponse;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class GetNewKud {

    @Test
    public void shouldCreateKud() {
        // 1. получить токен Basic
        String basicAuthToken = getBasicAuthToken();
        Assert.assertNotNull(basicAuthToken);
        // 2. получить токен OAuth
        ValidatableResponse responseWithToken = getResponseWithToken();
        Assert.assertNotNull(responseWithToken);

        // 3. получить employee_id and structure_id
        ValidatableResponse responseOfGetEmployeeIDData = getEmployeeIdAndAccessToken(responseWithToken);
        String token = responseWithToken.extract().path("access_token");
        Integer employeeId = responseWithToken.extract().path("employee_id");

        System.out.println(token + "\n++++++ employeeId " + employeeId + "+++++employeeId ");
        Assert.assertNotNull(responseOfGetEmployeeIDData);
        // 4. создать куд
        ValidatableResponse kudResponse = getKud(token, responseOfGetEmployeeIDData);
        Integer structureId = responseWithToken.extract().path("structureId");
        System.out.println(token + "\n++++++ structureId " + structureId + "+++++structureId ");
        kudResponse.assertThat().statusCode(200);
    }


    public String getBasicAuthToken() {
        String response = given()
                .when()
                .get(" http://turbo4.apps.sodch.phoenixit.ru/js/config.js")
                .then()
                .extract().body().asString();
        System.out.println("\n Responce getBasicAuthToken  " + response + "\n Responce getBasicAuthToken ");
        int indexStart = response.indexOf("Basic");
        int indexEnd = response.indexOf("==");
        System.out.println("indexStart = " + indexStart + " " + "indexEnd = " + indexEnd);
        String value = response.substring(indexStart, indexEnd + 2);
        System.out.println(value + "\nResponce getBasicAuthToken  END\n");
        return value;
    }

    public ValidatableResponse getResponseWithToken() {
        System.out.println("Start of getResponseWithToken");
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

    public ValidatableResponse getEmployeeIdAndAccessToken(ValidatableResponse responseWithToken) {


        String token = responseWithToken.extract().path("access_token");
        Integer employeeId = responseWithToken.extract().path("employee_id");
        System.out.println(token + "\n++++++++++employeeId = " + employeeId + "\n");
        System.out.println(token + "\n++++++++++access_token = " + token + "\n");
        return given()
                .header("Authorization", "Bearer " + token)
                .header("Connection", "keep-alive")
                .when()
                .get("http://turbo4.apps.sodch.phoenixit.ru/gateway/dictionary-service/api/v1/employee/" + employeeId)
                .then()
                .log().all()
                .log().ifError();

    }


    public ValidatableResponse getKud(String token, ValidatableResponse responseOfGetEmployeeIDData) {

        Integer structureId = responseOfGetEmployeeIDData.extract().path("data.structureId");
        System.out.println("Token = " + token + "\n+++ structureId =  " + structureId + " +++");
        ValidatableResponse responseGetDataKudNumber =
                given()
                        .header("Authorization", "Bearer " + token)
                        .header("Connection", "keep-alive")
                        .when()
                        .post("http://turbo4.apps.sodch.phoenixit.ru/gateway/kud-service/api/v1/kud/number?structureId=" + structureId)
                        .then()
                        .log().all()
                        .log().ifError();

        System.out.println("data responseGetDataKudNumber =  " + responseGetDataKudNumber);
        Integer kudNumber = responseGetDataKudNumber
                .extract().path("data");
        System.out.println("data kudNumber " + kudNumber + "\n structureId " + structureId);


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

                        .header("Host", "turbo4.apps.sodch.phoenixit.ru")
                        .header("Accept-Encoding", "gzip, deflate")
                        .body(bodyDataKud.toString())
                        .when()
                        .log().all()
                        .post("http://turbo4.apps.sodch.phoenixit.ru/gateway/kud-service/api/v1/kud/")
                        .then()
                        .log().all()
                        .log().ifError();
    }
}





