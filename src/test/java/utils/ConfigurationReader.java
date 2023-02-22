package utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    // 1-create an object from Properties class
    private static Properties properties = new Properties();

    private static final String PROPERTY_FILE_PATH = "src/test/resources/configuration.properties";
    private static final String CREDENTIALS_PROPERTY_FILE_PATH = "credentials.properties";

    static {

        try {
            // 2-we need to open the file in java memory: FileInputStream
            FileInputStream file = new FileInputStream(PROPERTY_FILE_PATH);

            // 3-Load the properties object using FileInputStream
            properties.load(file);

            // close the file
            file.close();

        } catch (IOException e) {
            System.out.println("File not found in the ConfigurationReader class.");
            e.printStackTrace();
        }
    }

    public static String getProperty(String keyword) {
        return properties.getProperty(keyword);
    }

    public static String getCredentialsProperty(String keyword) {
        try {
            FileInputStream file = new FileInputStream(CREDENTIALS_PROPERTY_FILE_PATH);
            properties.load(file);
            file.close();
        } catch (IOException e) {
            System.out.println("File not found in the ConfigurationReader class.");
            e.printStackTrace();
        }
        return properties.getProperty(keyword);
    }

    public static void updateCredentialsProperty(String keyword, String value) throws ConfigurationException {
        PropertiesConfiguration conf = new PropertiesConfiguration(CREDENTIALS_PROPERTY_FILE_PATH);
        conf.setProperty(keyword,value);
        conf.save();
    }
}