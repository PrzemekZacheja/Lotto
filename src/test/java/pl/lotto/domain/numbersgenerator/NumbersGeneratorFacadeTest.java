package pl.lotto.domain.numbersgenerator;

import org.junit.jupiter.api.*;
import pl.lotto.domain.numbersgenerator.dto.*;
import pl.lotto.domain.numbersreceiver.*;

import java.security.*;
import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NumbersGeneratorFacadeTest {

    private final NumberReceiverFacade mockNumberReceiverFacade = mock(NumberReceiverFacade.class);
    private final WinningNumberGeneratorForTest mockWinningNumberGeneratorForTest = mock(WinningNumberGeneratorForTest.class);
    private final NumbersGeneratorFacadeConfigurationProperties properties = NumbersGeneratorFacadeConfigurationProperties.builder()
                                                                                                                          .upperBand(
                                                                                                                                  99)
                                                                                                                          .lowerBand(
                                                                                                                                  1)
                                                                                                                          .count(25)
                                                                                                                          .build();

    NumbersGeneratorFacade generatorMockedNumbers = new NumbersGeneratorFacade(
            mockNumberReceiverFacade,
            new WinningNumberGeneratorForTest(),
            new NumbersGeneratorRepositoryForTest(),
            properties
    );

    NumbersGeneratorFacade generatorRandomNumbers = new NumbersGeneratorFacade(
            mockNumberReceiverFacade,
            new WinningNumberGenerator(new SecureRandom()),
            new NumbersGeneratorRepositoryForTest(),
            properties
    );

    @Test
    void should_generate_six_numbers() {
        //given
        final LocalDateTime drawDate = LocalDateTime.of(2023, 4, 1, 12, 0, 0);
        //when
        when(mockNumberReceiverFacade.getDrawDate()).thenReturn(drawDate);
        WinnerNumbersDto winnerNumbersDto = generatorRandomNumbers.generateSixNumbers();
        Set<Integer> winningNumbers = winnerNumbersDto.winningNumbers();
        //then
        assertThat(winningNumbers).hasSize(6);
    }

    @Test
    void should_generate_six_random_numbers() {
        //given
        final LocalDateTime drawDate = LocalDateTime.of(2023, 4, 1, 12, 0, 0);
        //when
        when(mockNumberReceiverFacade.getDrawDate()).thenReturn(drawDate);
        Set<Integer> randomNumbers1 = generatorRandomNumbers.generateSixNumbers()
                                                            .winningNumbers();
        Set<Integer> randomNumbers2 = generatorRandomNumbers.generateSixNumbers()
                                                            .winningNumbers();
        //then
        assertThat(randomNumbers1).isNotEqualTo(randomNumbers2);
    }

    @Test
    void should_return_correct_winning_numbers_by_date() {
        //given
        final LocalDateTime drawDate = LocalDateTime.of(2023, 4, 1, 12, 0, 0);
        when(mockNumberReceiverFacade.getDrawDate()).thenReturn(drawDate);
        //when
        WinnerNumbersDto winnerNumbersDto = generatorMockedNumbers.generateSixNumbers();
        WinnerNumbersDto winnerNumbersDtoFromRepository = generatorMockedNumbers.retrieveAllWinnerNumbersByNextDrawDate(
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
        WinnerNumbersDto winnerNumbersDto = generatorMockedNumbers.generateSixNumbers();
        //then
        assertThrows(WinningNunmbersNotFoundExeption.class,
                () -> generatorMockedNumbers.retrieveAllWinnerNumbersByNextDrawDate(failDrawDate));
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