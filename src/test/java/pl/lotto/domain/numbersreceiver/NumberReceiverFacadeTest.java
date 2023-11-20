package pl.lotto.domain.numbersreceiver;

import org.junit.jupiter.api.Test;
import pl.lotto.domain.AdjustableClock;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;
import pl.lotto.domain.drawdategenerator.DrawDateGenerable;
import pl.lotto.domain.drawdategenerator.DrawDateGeneratorForTest;
import pl.lotto.domain.numbersreceiver.dto.TicketDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class NumberReceiverFacadeTest {

    AdjustableClock clock = new AdjustableClock(LocalDateTime.of(2023, 4, 1, 12, 0, 0)
                                                             .toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
    DrawDateGenerable dateDrawGeneratorForTest = new DrawDateGeneratorForTest(clock);
    NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(
            new NumberValidator(),
            new InMemoryNumberReceiverRepositoryImpl(),
            new HashGenerator(),
            new DrawDateFacade(dateDrawGeneratorForTest)
    );

    @Test
    void should_return_success_when_user_gave_six_numbers() {
        //given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);
        //when
        TicketDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then
        assertThat(result.message()).isEqualTo("Success");
    }

    @Test
    void should_return_fail_when_user_gave_less_than_six_numbers() {
        //given
        Set<Integer> numbersFromUser = Set.of(2, 3, 4, 5, 6);
        //when
        TicketDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then
        assertThat(result.message()).isEqualTo("Fail");
    }

    @Test
    void should_return_fail_when_user_gave_more_than_six_numbers() {
        //given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6, 7);
        //when
        TicketDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then
        assertThat(result.message()).isEqualTo("Fail");
    }

    @Test
    void should_return_success_when_user_gave_six_numbers_in_range_from_1_to_99() {
        //given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);
        //when
        TicketDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then
        assertThat(result.message()).isEqualTo("Success");
    }

    @Test
    void should_return_fail_when_user_gave_six_numbers_out_of_range_from_1_to_99() {
        //given
        Set<Integer> numbersFromUser = Set.of(1000, 2, 3, 4, 5, 6);
        //when
        TicketDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then
        assertThat(result.message()).isEqualTo("Fail");
    }

    @Test
    void should_return_saved_object_when_user_gave_six_numbers() {
        //given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);
        TicketDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        LocalDateTime drawDate = LocalDateTime.of(2023, 4, 1, 12, 0, 0);
        clock = new AdjustableClock(drawDate.toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        DrawDateFacade drawDateFacade = new DrawDateFacade(new DrawDateGeneratorForTest(clock));
        //when
        List<TicketDto> ticketDtoToChanges = numberReceiverFacade.usersNumbers(drawDateFacade);
        //then
        assertThat(ticketDtoToChanges).contains(
                TicketDto.builder()
                         .ticketId(result.ticketId())
                         .drawDate(result.drawDate())
                         .userNumbers(result.userNumbers())
                         .build()
                                               );
    }

    @Test
    void should_return_correct_hash_for_returned_object() {
        //given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);
        //when
        TicketDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then
        assertThat(result.ticketId()).hasSize(36);
        assertThat(result.ticketId()).isNotNull();
    }

}