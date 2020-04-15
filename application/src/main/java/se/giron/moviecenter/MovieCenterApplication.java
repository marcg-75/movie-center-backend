package se.giron.moviecenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * Created by marc on 2020-04-15.
 */
@SpringBootApplication(scanBasePackages = "se.giron.moviecenter", exclude = UserDetailsServiceAutoConfiguration.class)
@EnableJpaRepositories("se.giron.moviecenter.core.repository")
@EntityScan(basePackages = {"se.giron.moviecenter.model.entity", "se.giron.moviecenter.model.entity.*", "se.giron.moviecenter.model.enums", "se.giron.moviecenter.core.configuration"})
@Import({WebConfig.class})
@EnableJpaAuditing
@EnableScheduling
@EnableAsync
@EnableConfigurationProperties
public class MovieCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieCenterApplication.class, args);
    }

    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Stockholm"));
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public ConversionService conversionService() {
        return new DefaultConversionService();
    }

}
