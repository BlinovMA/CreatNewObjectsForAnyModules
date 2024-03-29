package org.example;

import io.restassured.response.ValidatableResponse;
import org.json.JSONObject;
import static io.restassured.RestAssured.given;
import static utils.GetValueFromTurboPropertiesFile.baseURL;
import static utils.ServiceRequestEndpoints.incidentServiceGateawayEndpoint;


public class KUSPRequests {

    public static ValidatableResponse createKusp(String token) {
        int[] accidentHashtagIdsAray = new int[]{1};
        int[] accidentSpecialMarkIdsAray = new int[]{180001};
        ValidatableResponse responseGetDataKuspNumber =
                given()
                        .header("Authorization", "Bearer " + token)
                        .header("Connection", "keep-alive")
                        .when()
                        .log().all()
                        .get(baseURL() + incidentServiceGateawayEndpoint() + "kusp/sequence/KUSP_NUMBER")
                        .then()
                        .log().all()
                        .log().ifError();

        Integer kuspNumber = responseGetDataKuspNumber
                .extract().path("data");

        JSONObject complainantAddress = new JSONObject();
        complainantAddress.put("guid", "b741e447-56c5-46d9-b54a-e8b8521845c7");
        complainantAddress.put("addressText", "г. Москва, ул. Бориса Галушкина, д. 7");
        complainantAddress.put("aptNumber", "77");
        complainantAddress.put("countryId", 140376);

        JSONObject accidentAddress = new JSONObject();
        accidentAddress.put("guid", "b741e447-56c5-46d9-b54a-e8b8521845c7");
        accidentAddress.put("addressText", "г. Москва, ул. Бориса Галушкина, д. 7");
        accidentAddress.put("aptNumber", "77");

        JSONObject bodyData = new JSONObject();
        bodyData.put("number", kuspNumber);
        bodyData.put("regDate", "2022-09-24T15:00:41.899");
        bodyData.put("regDate", "2022-09-24T15:00:41.899");
        bodyData.put("sentToSummary", false);
        bodyData.put("incidentTypeId", 240001);
        bodyData.put("entryFormId", -100027);
        bodyData.put("receiveMethodId", -3001005);
        bodyData.put("complainantTypeId", -101000);
        bodyData.put("isTicketIssued", false);
        bodyData.put("complainantLegalName", "");
        bodyData.put("complainantLastName", "Воробьев");
        bodyData.put("complainantBirthdate", "2000-01-01T00:00:00.000");
        bodyData.put("complainantPhone", "+79091009009");
        bodyData.put("complainantFirstName", "Иван");
        bodyData.put("complainantNationalityId", -10184);
        bodyData.put("complainantMiddleName", "Михайлович");
        bodyData.put("complainantAddressExtraInfo", "Подъезд 2, 8 этаж");
        bodyData.put("complainantEmploymentPhone", "");
        bodyData.put("complainantSearchForJob", false);
        bodyData.put("complainantEmploymentPlace", "");
        bodyData.put("complainantEmploymentExtraInfo", "");
        bodyData.put("accidentForeign", false);
        bodyData.put("accidentTypeId", 6999969);
        bodyData.put("accidentStartDate", "2022-09-24T15:00:41.899");
        bodyData.put("accidentAddressExtraInfo", "Подъезд 2, 8 этаж");
        bodyData.put("accidentObjectId", 7483508);
        bodyData.put("accidentPlaceId", -9002);
        bodyData.put("accidentPrimaryMemo", "24.09.2022 15:00 г. Москва, ул. Бориса Галушкина, д. 7, произошло Кража из квартиры. Заявитель: Воробьев Иван Михайлович 01.01.2000, г. Москва, ул. Бориса Галушкина, д. 7.");
        bodyData.put("accidentMemo", "");
        bodyData.put("complainantEmploymentForeign", false);
        bodyData.put("accidentHashtagIds", accidentHashtagIdsAray);
        bodyData.put("accidentSpecialMarkIds", accidentSpecialMarkIdsAray);
        bodyData.put("routeOriginType", "OPER_DUTY");
        bodyData.put("accidentAddressGaspsFias", "b741e447-56c5-46d9-b54a-e8b8521845c7");
        bodyData.put("accidentAddressGaspsText", "г. Москва, ул. Бориса Галушкина, д. 7");
        bodyData.put("complainantAddress", complainantAddress);
        //  bodyData.put("complainantEmploymentAddress", null);
        bodyData.put("accidentAddress", accidentAddress);


        return
                given()
                        .header("Authorization", "Bearer " + token)
                        .header("Connection", "keep-alive")
                        .header("Content-Type", "application/json")
                        .body(bodyData.toString())
                        .when()
                        .log().all()
                        .post(baseURL() + incidentServiceGateawayEndpoint()+"kusp/")
                        .then()
                        .log().all()
                        .log().ifError();
    }


// "\"number\" : kuspNumber,
//         "regDate" + "2022-09-24T15:00:41.899",
//         "sentToSummary" + false,
//         "incidentTypeId" + 240001,
//         "entryFormId" + -100027,
//         "receiveMethodId" + -3001005,
//         "complainantTypeId" + -101000,
//         "isTicketIssued" + false,
//         "complainantLegalName" + "",
//         "complainantLastName" + "Воробьев",
//         "complainantBirthdate" + "2000-01-01T00:00:00.000",
//         "complainantPhone" + "+79091009009",
//         "complainantFirstName" + "Иван",
//         "complainantNationalityId" + -10184,
//         "complainantMiddleName" + "Михайлович",
//         "complainantAddressExtraInfo" + "Подъезд 2, 8 этаж",
//         "complainantEmploymentPhone" + "",
//         "complainantSearchForJob" + false,
//         "complainantEmploymentPlace" + "",
//         "complainantEmploymentExtraInfo" + "",
//         "accidentForeign" + false,
//         "accidentTypeId" + 6999969,
//         "accidentStartDate" + "2022-09-24T15:00:41.899",
//         "accidentAddressExtraInfo" + "Подъезд 2, 8 этаж",
//         "accidentObjectId" + 7483508,
//         "accidentPlaceId" + -9002,
//         "accidentPrimaryMemo" + "24.09.2022 15:00 г. Москва, ул. Бориса Галушкина, д. 7, произошло Кража из квартиры. Заявитель: Воробьев Иван Михайлович 01.01.2000, г. Москва, ул. Бориса Галушкина, д. 7.",
//         "accidentMemo" + "",
//         "complainantEmploymentForeign" + false,
//         "accidentHashtagIds" + "[1]",
//         "accidentSpecialMarkIds" + "[180001]",
//         "routeOriginType" + "OPER_DUTY",
//         "accidentAddressGaspsFias" + "b741e447-56c5-46d9-b54a-e8b8521845c7",
//         "accidentAddressGaspsText" + "г. Москва, ул. Бориса Галушкина, д. 7",
//         "complainantAddress" + "{" +
//         "guid" + "b741e447-56c5-46d9-b54a-e8b8521845c7",
//         "addressText" + "г. Москва, ул. Бориса Галушкина, д. 7",
//         "aptNumber" + "77",
//         "countryId" + "140376" + "}",
//         "complainantEmploymentAddress" + null,
//         "accidentAddress" + "{" + "guid" + "b741e447-56c5-46d9-b54a-e8b8521845c7",
//         "addressText" + "г. Москва, ул. Бориса Галушкина, д. 7",
//         "aptNumber" + "77"

}