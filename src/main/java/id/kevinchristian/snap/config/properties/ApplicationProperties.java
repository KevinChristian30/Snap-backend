package id.kevinchristian.snap.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application.properties")
public class ApplicationProperties {
    private Integer tokenDurationInMinutes;
    private String tokenIssuer;
    private String key;
}
