package pl.lotto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.lotto.domain.LottoSpringBootApplication;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = LottoSpringBootApplication.class)
class BaseIntegrationTest {

    @Test
    void shouldTest(){
        assertTrue(true);
    }
}