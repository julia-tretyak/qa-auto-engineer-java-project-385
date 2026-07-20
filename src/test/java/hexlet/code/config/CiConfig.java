package hexlet.code.config;

public class CiConfig implements TestConfig {

    @Override
    public String getBaseUrl() {
        return System.getenv("APP_BASE_URL");
    }

    @Override
    public boolean isHeadless() {
        return true;
    }
}
