package pl.lotto.domain.drawdategenerator;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class DrawDateFacadeTest {

    Clock clock = Clock.fixed(LocalDateTime.of(2023, 4, 1, 12, 0, 0)
                                           .toInstant(ZoneOffset.UTC),
                              ZoneId.of("Europe/London"));
    DrawDateFacade drawDateFacade = new DrawDateFacade(
            new DrawDateGenerator(clock)
    );

    @Test
    void it_should_return_next_Saturday_draw_date_when_date_is_Saturday_noon() {
        //given

        LocalDateTime expected = LocalDateTime.of(2023, 4, 8, 12, 0, 0);
        //when
        LocalDateTime dateOfNextDraw = drawDateFacade.generateDateOfNextDraw();
        //then
        assertThat(expected).isEqualTo(dateOfNextDraw);
    }

    @Test
    void it_should_return_this_Saturday_draw_date_when_date_is_before_Saturday_noon() {
        //given
        clock = Clock.fixed(LocalDateTime.of(2023, 3, 30, 15, 0, 0)
                                         .toInstant(ZoneOffset.UTC),
                            ZoneId.of("Europe/London"));
        drawDateFacade = new DrawDateFacade(new DrawDateGenerator(clock));

        LocalDateTime expected = LocalDateTime.of(2023, 4, 1, 12, 0, 0);
        //when
        LocalDateTime dateOfNextDraw = drawDateFacade.generateDateOfNextDraw();
        //then
        assertThat(expected).isEqualTo(dateOfNextDraw);
    }

    @Test
    void it_should_return_next_Saturday_draw_date_when_date_is_Saturday_afternoon() {
        //given
        clock = Clock.fixed(LocalDateTime.of(2023, 4, 1, 15, 0, 0)
                                         .toInstant(ZoneOffset.UTC),
                            ZoneId.of("Europe/London"));
        LocalDateTime expected = LocalDateTime.of(2023, 4, 8, 12, 0, 0);
        //when
        LocalDateTime dateOfNextDraw = drawDateFacade.generateDateOfNextDraw();
        //then
        assertThat(expected).isEqualTo(dateOfNextDraw);
    }

}