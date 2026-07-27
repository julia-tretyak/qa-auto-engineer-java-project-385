package hexlet.code.config;

public class LocalConfig implements TestConfig {

    @Override
    public String getBaseUrl() {
        return "http://localhost:5173";
    }

    @Override
    public boolean isHeadless() {
        return false;
    }
}
