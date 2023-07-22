package pl.lotto;

import org.springframework.context.annotation.*;
import pl.lotto.domain.*;

import java.time.*;

@Configuration
@Profile("integration")
public class IntegrationConfiguration {

    @Bean
    @Primary
    AdjustableClock clock() {
        return AdjustableClock.ofLocalDateAndLocalTime(LocalDate.of(2022, 11, 25),
                LocalTime.of(10, 0, 0),
                ZoneId.systemDefault());
    }
}