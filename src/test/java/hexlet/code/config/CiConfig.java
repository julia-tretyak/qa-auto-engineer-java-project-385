package hexlet.code.config;

public class CiConfig implements TestConfig {

    @Override
    public String getBaseUrl() {
        String url = System.getenv("APP_BASE_URL");
        return (url != null && !url.isEmpty()) ? url : "http://localhost:5173";
    }

    @Override
    public boolean isHeadless() {
        return true;
    }
}
