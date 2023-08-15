package utils;

public class ServiceRequestEndpoints {


    public ServiceRequestEndpoints() {
    }

    public static String incidentServiceGateawayEndpoint(){

        return "gateway/incident-service/api/v1/";
    }

    public static String kudServiceGateawayEndpoint() {

        return  "gateway/kud-service/api/v1/";
    }


    public static String wantedServiceGateawayEndpoint() {
        return "gateway/wanted-service/api/v1/";
    }

    public static String dictionaryServiceGateawayEndpoint() {
        return "gateway/dictionary-service/api/v1/";
    }
}




// Ниже варианты записи одно и того же метожв для педачи значения части URL


//    private static String wantedServiceGateawayEndpoint = "gateway/wanted-service/api/v1/";
//
//    public static String wantedServiceGateawayEndpoint() {
//        return wantedServiceGateawayEndpoint;
//    }



//public static String wantedServiceGateawayEndpoint() {
//    String wantedServiceGateawayEndpoint = "gateway/wanted-service/api/v1/";
//    return wantedServiceGateawayEndpoint;
//}