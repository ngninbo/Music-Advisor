package advisor.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertiesLoader {

    public static Properties loadProperties(String resourceFileName) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties configuration = new Properties();
        InputStream inputStream = loader
                .getResourceAsStream(resourceFileName);
        configuration.load(inputStream);
        Objects.requireNonNull(inputStream).close();
        return configuration;
    }
}
