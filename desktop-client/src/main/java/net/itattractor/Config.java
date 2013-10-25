package net.itattractor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Properties properties;

    private Config() {
    }

    public static void init() {
        InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("tracker.properties");
        properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }
    public static void setValue(String key,String value) {
        properties.setProperty(key,value);
    }
}
