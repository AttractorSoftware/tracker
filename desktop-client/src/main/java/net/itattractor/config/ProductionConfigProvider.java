package net.itattractor.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProductionConfigProvider implements Config {
    public static final String PRODUCTION_PROPERTIES = "production.properties";
    private Properties properties;

    public ProductionConfigProvider() {
        InputStream inputStream = ProductionConfigProvider.class.getClassLoader().getResourceAsStream(PRODUCTION_PROPERTIES);
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
