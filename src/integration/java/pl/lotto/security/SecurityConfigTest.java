package pl.lotto.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.lotto.domain.AdjustableClock;

import java.time.Clock;

@Configuration
@Profile("security-integration")
public class SecurityConfigTest {

    @Bean
    Clock clock() {
        return AdjustableClock.systemUTC();
    }

}