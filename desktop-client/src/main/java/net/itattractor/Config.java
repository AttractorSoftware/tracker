package net.itattractor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Properties properties;

    private Config() {
    }

    public static void init(String mode) {
        InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(String.format("%s.properties", mode));
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
}
