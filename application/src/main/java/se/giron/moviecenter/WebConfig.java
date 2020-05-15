package se.giron.moviecenter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static java.util.Optional.ofNullable;

@Configuration
@ConfigurationProperties(prefix = "moviecenter.cors")
public class WebConfig implements WebMvcConfigurer {

    private static final String[] EMPTY_LIST = {};
    private String[] allowedOrigins;

    public void addCorsMappings(CorsRegistry registry) {
        CorsRegistration corsRegistration = registry.addMapping("/**");
        //corsRegistration.allowedOrigins(getAllowedOrigins());
        corsRegistration.allowedOrigins("*");
        corsRegistration.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }

    public String[] getAllowedOrigins() {
        return ofNullable(allowedOrigins).orElse(EMPTY_LIST);
    }

    public void setAllowedOrigins(String... allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }
}