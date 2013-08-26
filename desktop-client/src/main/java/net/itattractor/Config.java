package net.itattractor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static Properties properties;

    private Config() {
    }

    public static void init() {
        properties = new Properties();
        try {
            FileInputStream inputStream = new FileInputStream("src/main/resources/tracker.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }
}
