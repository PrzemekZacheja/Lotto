package pl.lotto.domain.numbers_receiver;

import org.junit.jupiter.api.Test;
import pl.lotto.domain.AdjustableClock;
import pl.lotto.domain.numbers_receiver.dto.InputNumbersResultDto;
import pl.lotto.domain.numbers_receiver.dto.TicketDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class NumberReceiverFacadeTest {

    AdjustableClock clock = new AdjustableClock(LocalDateTime.of(2023,3,31,10,0,0).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());

    NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(
            new NumberValidator(),
            new InMemoryNumberReceiverRepositoryImpl(),
            clock
    );

    @Test
    void should_return_success_when_user_gave_six_numbers() {
        //given
        Set<Integer>  numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);
        //when
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers( numbersFromUser);
        //then
        assertThat(result.message()).isEqualTo("Success");
    }

    @Test
    void should_return_fail_when_user_gave_less_than_six_numbers() {
        //given
        Set<Integer> numbersFromUser = Set.of(2, 3, 4, 5, 6);
        //when
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then
        assertThat(result.message()).isEqualTo("Fail");
    }

    @Test
    void should_return_fail_when_user_gave_more_than_six_numbers() {
        //given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6, 7);
        //when
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then
        assertThat(result.message()).isEqualTo("Fail");
    }

    @Test
    void should_return_success_when_user_gave_six_numbers_in_range_from_1_to_99() {
        //given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);
        //when
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then
        assertThat(result.message()).isEqualTo("Success");
    }

    @Test
    void should_return_fail_when_user_gave_six_numbers_out_of_range_from_1_to_99() {
        //given
        Set<Integer> numbersFromUser = Set.of(1000, 2, 3, 4, 5, 6);
        //when
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then
        assertThat(result.message()).isEqualTo("Fail");
    }

    @Test
    void should_return_saved_object_when_user_gave_six_numbers(){
        //given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        LocalDateTime drawDate = LocalDateTime.of(2023, 3, 31, 12, 0, 0);
        //when
        List<TicketDto> ticketDtos = numberReceiverFacade.usersNumbers(drawDate);
        //then
        assertThat(ticketDtos).contains(
                TicketDto.builder()
                        .ticketId(result.ticketId())
                        .drawDate(result.drawDate())
                        .numbersFromUser(result.userNumbers())
                        .build()

        );
    }
}