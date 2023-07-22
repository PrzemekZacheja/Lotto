package pl.lotto.domain.drawdategenerator;

import org.springframework.context.annotation.*;

import java.time.*;

@Configuration
public class DrawDateGeneratorConfiguration {

    @Bean
    DrawDateFacade drawDateFacade() {
        return new DrawDateFacade(drawDateGenerator());
    }

    @Bean
    DrawDateGenerator drawDateGenerator() {
        return new DrawDateGenerator(clock());
    }

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }


}