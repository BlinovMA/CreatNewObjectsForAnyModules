package org.example;

import io.restassured.response.ValidatableResponse;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.GetValueFromTurboPropertiesFile;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class MethodForGenerateNewKud {

    public static ValidatableResponse getEmployeeIdWithAccessToken(ValidatableResponse responseWithToken) {

        String token = responseWithToken.extract().path("access_token");
        Integer employeeId = responseWithToken.extract().path("employee_id");
        System.out.println(token + "\n++++++++++employeeId = " + employeeId + "\n");
              return given()
                .header("Authorization", "Bearer " + token)
                .header("Connection", "keep-alive")
                .when()
                .get(GetValueFromTurboPropertiesFile.baseURL+"gateway/dictionary-service/api/v1/employee/" + employeeId)
                .then()
                .log().all()
                .log().ifError();
    }

    public static ValidatableResponse createKud(String token, ValidatableResponse responseOfGetEmployeeIDData) {

        Integer structureId = responseOfGetEmployeeIDData.extract().path("data.structureId");
        System.out.println("Token = " + token + "\n+++ structureId =  " + structureId + " +++");
        ValidatableResponse responseGetDataKudNumber =
                given()
                        .header("Authorization", "Bearer " + token)
                        .header("Connection", "keep-alive")
                        .when()
                        .post(GetValueFromTurboPropertiesFile.baseURL+"gateway/kud-service/api/v1/kud/number?structureId=" + structureId)
                        .then()
                        .log().all()
                        .log().ifError();

        System.out.println("data responseGetDataKudNumber =  " + responseGetDataKudNumber);
        Integer kudNumber = responseGetDataKudNumber.extract().path("data");
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
                        .post(GetValueFromTurboPropertiesFile.baseURL+"gateway/kud-service/api/v1/kud/")
                        .then()
                        .log().all()
                        .log().ifError();
    }
}





