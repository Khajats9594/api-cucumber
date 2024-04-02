package utiles;

import contants.FrameworkConstants;
import enums.ConfigProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public final class PropertyUtils {

    private PropertyUtils() {
    }
    private static Properties property = new Properties();
    private static final Map<String, String> CONFIGMAP = new HashMap<>();

    static {
        try (FileInputStream file = new FileInputStream(FrameworkConstants.getConfigFilePath())) {
            property.load(file);
            for (Map.Entry<Object, Object> entry : property.entrySet()) {
                CONFIGMAP.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()).trim()); //remove the trailing and leading spaces
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    public static String get(ConfigProperties key) {
        String sysPropertyValue = System.getProperty(key.name().toLowerCase());
        if (Objects.nonNull(sysPropertyValue)) {
            return sysPropertyValue;
        }
        String configMapValue = CONFIGMAP.get(key.name().toLowerCase());
        if (Objects.nonNull(configMapValue)) {
            return configMapValue;
        }
        throw new RuntimeException("Property name " + key + " is not found. Please check config.properties");
    }
}
