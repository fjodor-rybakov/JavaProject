package app;

import app.interfaces.IConfiguration;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {
    @Test
    public void GetThreadsCountFromConfiguration() {
        try {
            IConfiguration configuration = new Configuration("src/test/java/properties.test.xml");
            String threadsStr = configuration
                    .getSingleByTagName("app-settings")
                    .getSingleByTagName("thread-pools")
                    .getBody();
            assertEquals("3", threadsStr);
        } catch (IOException ignored) {
        }
    }
}