package pl.lotto;

import org.springframework.context.annotation.*;
import org.springframework.context.support.*;
import org.springframework.core.io.*;

@Configuration
public class AppConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource("application.properties"));
        return configurer;
    }
}