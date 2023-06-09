package pl.lotto.domain.drawdategenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class DrawDateGeneratorConfiguration {

    @Bean
    DrawDateFacade drawDateFacade() {
        return new DrawDateFacade(new DrawDateGenerator(Clock.systemUTC()));
    }

}