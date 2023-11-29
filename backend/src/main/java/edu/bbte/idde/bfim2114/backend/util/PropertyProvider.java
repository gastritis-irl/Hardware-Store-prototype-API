package edu.bbte.idde.bfim2114.backend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyProvider {
    private static final Logger LOG = LoggerFactory.getLogger(PropertyProvider.class);
    private static final String PROP_FILE_NAME = "application";
    private static final Properties properties;

    static {
        properties = new Properties();

        LOG.info("Loading properties");
        LOG.info("Attempting to load properties from {}", PROP_FILE_NAME + ".properties");
        try (InputStream inputStream =
                     PropertyProvider.class.getResourceAsStream('/' + PROP_FILE_NAME + ".properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            LOG.error("Error loading properties", e);
        }
    }

    public static String getProperty(final String key) {
        return properties.getProperty(key);
    }
}