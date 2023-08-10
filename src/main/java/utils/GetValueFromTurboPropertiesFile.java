package utils;

public class GetValueFromTurboPropertiesFile {

    static TurboPropertiesLoader propertyLoader = new TurboPropertiesLoader();
    public static String baseURL = propertyLoader.loadPropertiesFile("turbo.properties.properties").getProperty("base.url");
    public static String baseTokenEndPoint = propertyLoader.loadPropertiesFile("turbo.properties.properties").getProperty("base.auth.token.endpoint");


    public static String mainTokenEndPoint = propertyLoader.loadPropertiesFile("turbo.properties.properties").getProperty("main.auth.token.endpoint");
    public static String ovdUserLogin = propertyLoader.loadPropertiesFile("turbo.properties.properties").getProperty("ovd.user.login");
    public static String ovdPassword = propertyLoader.loadPropertiesFile("turbo.properties.properties").getProperty("ovd.password");

    public String uvdUserLogin = propertyLoader.loadPropertiesFile("turbo.properties.properties").getProperty("uvd.user.login");
    public String uvdPassword = propertyLoader.loadPropertiesFile("turbo.properties.properties").getProperty("uvd.password");
    public String guvdUserLogin = propertyLoader.loadPropertiesFile("turbo.properties.properties").getProperty("guvd.user.login");
    public String guvdPassword = propertyLoader.loadPropertiesFile("turbo.properties.properties").getProperty("guvd.password");

//    public String getovdUserLogin() {
//
//        return ovdUserLogin;
//    }
//
//    public String getovdPassword() {
//
//        return ovdPassword;
//    }

}
