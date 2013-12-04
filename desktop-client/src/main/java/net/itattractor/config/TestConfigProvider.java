package net.itattractor.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfigProvider implements Config {
    public static final String TEST_PROPERTIES = "test.properties";
    private Properties properties;

    public TestConfigProvider() {
        InputStream inputStream = ProductionConfigProvider.class.getClassLoader().getResourceAsStream(TEST_PROPERTIES);
        properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
