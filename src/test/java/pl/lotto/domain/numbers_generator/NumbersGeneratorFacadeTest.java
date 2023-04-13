package pl.lotto.domain.numbers_generator;

import org.junit.jupiter.api.Test;
import pl.lotto.domain.numbers_generator.dto.WinnerNumbersDto;
import pl.lotto.domain.numbers_receiver.NumberReceiverFacade;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NumbersGeneratorFacadeTest {

    private final NumberReceiverFacade mockNumberReceiverFacade = mock(NumberReceiverFacade.class);
    private final WinningNumberGeneratorForTest mockWinningNumberGeneratorForTest = mock(WinningNumberGeneratorForTest.class);

    NumbersGeneratorFacade generatorMockedNumbers = new NumbersGeneratorFacade(
            mockNumberReceiverFacade,
            new WinningNumberGeneratorForTest(),
            new NumbersGeneratorRepositoryForTest()
    );

    NumbersGeneratorFacade generatorRandomNumbers = new NumbersGeneratorFacade(
            mockNumberReceiverFacade,
            new WinningNumberGenerator(new SecureRandom()),
            new NumbersGeneratorRepositoryForTest()
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
        Set<Integer> randomNumbers1 = generatorRandomNumbers.generateSixNumbers().winningNumbers();
        Set<Integer> randomNumbers2 = generatorRandomNumbers.generateSixNumbers().winningNumbers();
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
        WinnerNumbersDto winnerNumbersDtoFromRepository = generatorMockedNumbers.retrieveAllWinnerNumbersByNextDrawDate(drawDate);
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
        assertThrows(WinningNunmbersNotFoundExeption.class, () -> generatorMockedNumbers.retrieveAllWinnerNumbersByNextDrawDate(failDrawDate));
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

    @Test
    void should_throw_exception_when_not_in_required_range() {
        //given
        final LocalDateTime drawDate = LocalDateTime.of(2023, 4, 1, 12, 0, 0);
        NumbersGeneratorFacade generatorMockedNumbers = new NumbersGeneratorFacade(
                mockNumberReceiverFacade,
                mockWinningNumberGeneratorForTest,
                new NumbersGeneratorRepositoryForTest()
        );
        //when
        when(mockNumberReceiverFacade.getDrawDate()).thenReturn(drawDate);
        when(mockWinningNumberGeneratorForTest.generateWinningRandomNumbers()).thenReturn(Set.of(100, 2, 3, 4, 5, 6));
        //then
        assertThrows(IllegalStateException.class, generatorMockedNumbers::generateSixNumbers);
    }

}