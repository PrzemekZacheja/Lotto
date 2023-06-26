package pl.lotto.domain.resultchecker;

import org.junit.jupiter.api.Test;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;
import pl.lotto.domain.numbersgenerator.NumbersGeneratorFacade;
import pl.lotto.domain.numbersgenerator.dto.WinnerNumbersDto;
import pl.lotto.domain.numbersreceiver.NumberReceiverFacade;
import pl.lotto.domain.numbersreceiver.dto.TicketDto;
import pl.lotto.domain.resultchecker.dto.TicketCheckedDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResultCheckerFacadeTest {

    NumbersGeneratorFacade numbersGenerator = mock(NumbersGeneratorFacade.class);
    NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
    DrawDateFacade drawDateFacade = mock(DrawDateFacade.class);
    TicketCheckedRepositoryImpl ticketCheckedRepositoryForTesting = new TicketCheckedRepositoryImpl();
    ResultCheckerFacade resultCheckerFacade = new ResultCheckerFacade(
            numbersGenerator,
            numberReceiverFacade,
            drawDateFacade,
            ticketCheckedRepositoryForTesting,
            new ResultChecker(ticketCheckedRepositoryForTesting)
    );


    @Test
    void should_generate_all_players_with_correct_message() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2023, 3, 31, 12, 0, 0);
        when(numbersGenerator.retrieveAllWinnerNumbersByNextDrawDate(localDateTime))
                .thenReturn(new WinnerNumbersDto(localDateTime, Set.of(1, 2, 3, 4, 5, 6)));
        when(numberReceiverFacade.retrieveAllTicketsByNextDrawDate(localDateTime)).thenReturn(
                List.of(
                        TicketDto.builder()
                                .userNumbers(Set.of(1, 2, 3, 9, 8, 7))
                                .drawDate(localDateTime)
                                .ticketId("001")
                                .build(),
                        TicketDto.builder()
                                .userNumbers(Set.of(1, 2, 30, 40, 80, 70))
                                .drawDate(localDateTime)
                                .ticketId("002")
                                .build()
                ));
        when(drawDateFacade.getDateOfNextDraw()).thenReturn(localDateTime);
        //when
        List<TicketCheckedDto> ticketCheckedDtos = resultCheckerFacade.generateWinningTickets();
        //then
        assertThat(ticketCheckedDtos).isEqualTo(
                List.of(
                        TicketCheckedDto.builder()
                                .numbersFromUser(Set.of(1, 2, 3, 9, 8, 7))
                                .winnersNumbers(Set.of(1, 2, 3, 4, 5, 6))
                                .drawDate(localDateTime)
                                .ticketId("001")
                                .isWinner(true)
                                .message("Ticket checked correctly")
                                .build(),
                        TicketCheckedDto.builder()
                                .numbersFromUser(Set.of(1, 2, 30, 40, 80, 70))
                                .winnersNumbers(Set.of(1, 2, 3, 4, 5, 6))
                                .drawDate(localDateTime)
                                .ticketId("002")
                                .isWinner(false)
                                .message("Ticket checked correctly")
                                .build()
                ));
    }

    @Test
    void should_generate_fail_message_when_winningNumbers_equal_null() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2023, 3, 31, 12, 0, 0);
        when(numbersGenerator.retrieveAllWinnerNumbersByNextDrawDate(localDateTime))
                .thenReturn(null);
        when(numberReceiverFacade.retrieveAllTicketsByNextDrawDate(localDateTime)).thenReturn(
                List.of(
                        TicketDto.builder()
                                .userNumbers(Set.of(1, 2, 3, 9, 8, 7))
                                .drawDate(localDateTime)
                                .ticketId("001")
                                .build()
                ));
        when(drawDateFacade.getDateOfNextDraw()).thenReturn(localDateTime);
        //when
        List<TicketCheckedDto> ticketCheckedDtos = resultCheckerFacade.generateWinningTickets();
        //then
        assertThat(ticketCheckedDtos.get(0)
                .message()).isEqualTo("Ticket checked incorrectly");
    }

    @Test
    void should_generate_fail_message_when_winningNumbers_is_empty() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2023, 3, 31, 12, 0, 0);
        when(numbersGenerator.retrieveAllWinnerNumbersByNextDrawDate(localDateTime))
                .thenReturn(null);
        when(numberReceiverFacade.retrieveAllTicketsByNextDrawDate(localDateTime)).thenReturn(
                List.of(
                        TicketDto.builder()
                                .userNumbers(Set.of(1, 2, 3, 9, 8, 7))
                                .drawDate(localDateTime)
                                .ticketId("001")
                                .build()

                ));
        when(drawDateFacade.getDateOfNextDraw()).thenReturn(localDateTime);
        //when
        List<TicketCheckedDto> ticketCheckedDtos = resultCheckerFacade.generateWinningTickets();
        //then
        assertThat(ticketCheckedDtos.get(0)
                .message()).isEqualTo("Ticket checked incorrectly");
    }

    @Test
    void should_save_correct_objects_to_database() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2023, 3, 31, 12, 0, 0);
        when(numbersGenerator.retrieveAllWinnerNumbersByNextDrawDate(localDateTime))
                .thenReturn(new WinnerNumbersDto(localDateTime, Set.of(1, 2, 3, 4, 5, 6)));
        when(numberReceiverFacade.retrieveAllTicketsByNextDrawDate(localDateTime)).thenReturn(
                List.of(
                        TicketDto.builder()
                                .userNumbers(Set.of(1, 2, 3, 9, 8, 7))
                                .drawDate(localDateTime)
                                .ticketId("001")
                                .build()
                ));
        when(drawDateFacade.getDateOfNextDraw()).thenReturn(localDateTime);
        //when
        List<TicketCheckedDto> listFromDatabase = resultCheckerFacade.generateWinningTickets();
        List<TicketChecked> ticketCheckedList = ResultCheckerMapper.mapToTicketChecked(listFromDatabase);
        //then
        assertThat(ticketCheckedList).isEqualTo(List.of(
                TicketChecked.builder()
                        .winnersNumbers(Set.of(1, 2, 3, 4, 5, 6))
                        .numbersFromUser(Set.of(1, 2, 3, 9, 8, 7))
                        .drawDate(localDateTime)
                        .ticketId("001")
                        .isWinner(true)
                        .message("Ticket checked correctly")
                        .build()
        ));
    }

    @Test
    void should_return_correct_objects_from_database() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2023, 3, 31, 12, 0, 0);
        when(numbersGenerator.retrieveAllWinnerNumbersByNextDrawDate(localDateTime))
                .thenReturn(new WinnerNumbersDto(localDateTime, Set.of(1, 2, 3, 4, 5, 6)));
        when(numberReceiverFacade.retrieveAllTicketsByNextDrawDate(localDateTime)).thenReturn(
                List.of(
                        TicketDto.builder()
                                .userNumbers(Set.of(1, 2, 3, 9, 8, 7))
                                .drawDate(localDateTime)
                                .ticketId("001")
                                .build()

                ));
        when(drawDateFacade.getDateOfNextDraw()).thenReturn(localDateTime);
        //when
        List<TicketCheckedDto> listFromDatabase = resultCheckerFacade.generateWinningTickets();
        List<TicketChecked> ticketCheckedList = ResultCheckerMapper.mapToTicketChecked(listFromDatabase);
        //then
        assertThat(ticketCheckedList).isEqualTo(List.of(
                TicketChecked.builder()
                        .winnersNumbers(Set.of(1, 2, 3, 4, 5, 6))
                        .numbersFromUser(Set.of(1, 2, 3, 9, 8, 7))
                        .drawDate(localDateTime)
                        .ticketId("001")
                        .isWinner(true)
                        .message("Ticket checked correctly")
                        .build()
        ));
    }
}