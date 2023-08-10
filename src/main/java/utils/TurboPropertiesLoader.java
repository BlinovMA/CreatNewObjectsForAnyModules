package utils;



import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TurboPropertiesLoader {

           public Properties loadPropertiesFile(String propFileName) {
            Properties prop = new Properties();
            try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(propFileName)) {
                prop.load(resourceAsStream);
            } catch (IOException e) {
                System.err.println("Unable to load properties file : " + propFileName);
            }
            return prop;
        }

    }
