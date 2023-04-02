package pl.lotto.domain.numbers_generator;

import org.junit.jupiter.api.Test;
import pl.lotto.domain.numbers_generator.dto.WinnerNumbersDto;
import pl.lotto.domain.numbers_receiver.NumberReceiverFacade;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NumbersGeneratorFacadeTest {

    private final NumberReceiverFacade mockNumberReceiverFacade = mock(NumberReceiverFacade.class);

    NumbersGeneratorFacade generatorMockedNumbers = new NumbersGeneratorFacade(
            mockNumberReceiverFacade,
            new WinningNumberGeneratorForTest(),
            new NumbersGeneratorRepositoryForTest() {
            }
    );

    NumbersGeneratorFacade generatorRandomNumbers = new NumbersGeneratorFacade(
            mockNumberReceiverFacade,
            new WinningNumberGenerator(new SecureRandom()),
            new NumbersGeneratorRepositoryForTest()
    );

    @Test
    void should_generate_six_numbers() {
        //given
        final LocalDateTime drawDate = LocalDateTime.of(2023, 4, 1, 12,0,0);
        when(generatorMockedNumbers.generateSixNumbers().timeOfWinDrawNumbers()).thenReturn(drawDate);
        //when
        WinnerNumbersDto winnerNumbersDto = generatorRandomNumbers.generateSixNumbers();
        Set<Integer> winningNumbers = winnerNumbersDto.winningNumbers();
        //then
        assertThat(winningNumbers.size()).isEqualTo(6);
    }

    @Test
    void should_generate_six_random_numbers() {
        //given
        //when
        Set<Integer> randomNumbers1 = generatorRandomNumbers.generateSixNumbers().winningNumbers();
        Set<Integer> randomNumbers2 = generatorRandomNumbers.generateSixNumbers().winningNumbers();
        //then
        assertThat(randomNumbers1).isNotEqualTo(randomNumbers2);
    }

    @Test
    void should_return_correct_saved_object_to_database(){
        //given

        //when

    }

    @Test
    void should_return_correct_object_dto_from_database(){

    }
}