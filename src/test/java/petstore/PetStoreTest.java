package petstore;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class PetStoreTest {

    final static Properties CONFIG = new Properties();

    private static void loadProperties() throws IOException {
        try (InputStream in = PetStoreTest.class.getClassLoader().getResourceAsStream("test.properties")) {
            if (in == null) {
                throw new RuntimeException("file not found");
            }
            CONFIG.load(in);
        }
    }

    public static String getProperty(String prop) {
        String property = CONFIG.getProperty(prop);
        assertThat(property, notNullValue());
        return property;
    }

    static {
        try {
            loadProperties();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
