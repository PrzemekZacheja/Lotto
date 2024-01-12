package pl.lotto.domain.numbersgenerator;

import org.junit.jupiter.api.Test;
import pl.lotto.domain.AdjustableClock;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;
import pl.lotto.domain.drawdategenerator.DrawDateGeneratorForTest;
import pl.lotto.domain.numbersgenerator.dto.WinnerNumbersDto;
import pl.lotto.domain.numbersreceiver.NumberReceiverFacade;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NumbersGeneratorFacadeTest {

    final LocalDateTime drawDate = LocalDateTime.of(2023, 4, 1, 12, 0, 0);
    final AdjustableClock clock = new AdjustableClock(drawDate.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
    private final NumberReceiverFacade mockNumberReceiverFacade = mock(NumberReceiverFacade.class);
    private final NumbersGeneratorFacadeConfigurationProperties properties =
            NumbersGeneratorFacadeConfigurationProperties.builder()
                                                                                                                          .upperBand(
                                                                                                                                  99)
                                                                                                                          .lowerBand(
                                                                                                                                  1)
                                                                                                                          .count(25)
                                                                                                                          .build();
    final NumbersGeneratorFacade generatorMockedNumbers = new NumbersGeneratorFacade(
            new WinningNumberGeneratorForTest(),
            new NumbersGeneratorRepositoryForTest(),
            properties,
            new DrawDateFacade(new DrawDateGeneratorForTest(clock))
    );
    final NumbersGeneratorFacade generatorRandomNumbers = new NumbersGeneratorFacade(
            new WinningNumberGenerator(new SecureRandom()),
            new NumbersGeneratorRepositoryForTest(),
            properties,
            new DrawDateFacade(new DrawDateGeneratorForTest(clock)
            ));

    @Test
    void should_generate_six_numbers() {
        //given

        //when
        when(mockNumberReceiverFacade.getDrawDate()).thenReturn(drawDate);
        WinnerNumbersDto winnerNumbersDto = generatorRandomNumbers.generateSixNumbers();
        Set<Integer> winningNumbers = winnerNumbersDto.winningNumbers();
        //then
        assertThat(winningNumbers).hasSize(6);
    }

    @Test
    void should_generate_one_set_six_random_numbers_by_givn_date() {
        //given
        final LocalDateTime drawDate = LocalDateTime.of(2023, 4, 1, 12, 0, 0);
        //when
        when(mockNumberReceiverFacade.getDrawDate()).thenReturn(drawDate);
        Set<Integer> randomNumbers1 = generatorRandomNumbers.generateSixNumbers()
                                                            .winningNumbers();
        Set<Integer> randomNumbers2 = generatorRandomNumbers.generateSixNumbers()
                                                            .winningNumbers();
        //then
        assertThat(randomNumbers1).isEqualTo(randomNumbers2);
    }

    @Test
    void should_return_correct_winning_numbers_by_date() {
        //given
        final LocalDateTime drawDate = LocalDateTime.of(2023, 4, 1, 12, 0, 0);
        when(mockNumberReceiverFacade.getDrawDate()).thenReturn(drawDate);
        //when
        WinnerNumbersDto winnerNumbersDto = generatorMockedNumbers.generateSixNumbers();
        WinnerNumbersDto winnerNumbersDtoFromRepository = generatorMockedNumbers.retrieveAllWinnerNumbersByDrawDate(
                drawDate);
        //then
        assertThat(winnerNumbersDtoFromRepository).isEqualTo(winnerNumbersDto);
    }

    @Test
    void should_throw_exception_when_not_retrieved_numbers_by_date() {
        //given
        final LocalDateTime drawDate = LocalDateTime.of(2023, 4, 1, 12, 0, 0);
        final LocalDateTime failDrawDate = LocalDateTime.of(2021, 4, 1, 12, 0, 0);
        when(mockNumberReceiverFacade.getDrawDate()).thenReturn(drawDate);
        //when
        generatorMockedNumbers.generateSixNumbers();
        //then
        assertThrows(WinningNumbersNotFoundException.class,
                     () -> generatorMockedNumbers.retrieveAllWinnerNumbersByDrawDate(failDrawDate));
    }

    @Test
    void should_generate_six_numbers_in_required_range() {
        //given
        final LocalDateTime drawDate = LocalDateTime.of(2023, 4, 1, 12, 0, 0);
        //when
        when(mockNumberReceiverFacade.getDrawDate()).thenReturn(drawDate);
        WinnerNumbersDto winnerNumbersDto = generatorRandomNumbers.generateSixNumbers();
        Set<Integer> winningNumbers = winnerNumbersDto.winningNumbers();
        //then
        assertThat(winningNumbers).hasSize(6);
    }

}