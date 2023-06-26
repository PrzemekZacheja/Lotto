package pl.lotto.domain.resultannouncer;

import org.junit.jupiter.api.Test;
import pl.lotto.domain.AdjustableClock;
import pl.lotto.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;
import pl.lotto.domain.resultchecker.dto.TicketCheckedDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResultAnnouncerFacadeTest {

    ResultCheckerFacade resultCheckerFacadeMock = mock(ResultCheckerFacade.class);
    RepositoryForTest repository = new RepositoryForTest();
    ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerFacade(resultCheckerFacadeMock, repository);
    AdjustableClock clockForTest = new AdjustableClock(LocalDateTime.of(2023, 4, 1, 12, 0, 0)
            .toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
    String idTicket = "001";


    @Test
    void should_return_correct_ResponseDto_object_by_idTicket() {
        //given

        when(resultCheckerFacadeMock.retrieveTicketCheckedByIdTicket(idTicket)).thenReturn(
                TicketCheckedDto.builder()
                        .ticketId("001")
                        .winnersNumbers(Set.of(1, 2, 3, 4, 5, 6))
                        .numbersFromUser(Set.of(1, 2, 3, 4, 5, 6))
                        .drawDate(LocalDateTime.now(clockForTest))
                        .message("You win")
                        .isWinner(true)
                        .build()
        );
        //when
        ResultAnnouncerResponseDto responseDto = resultAnnouncerFacade.generateResponseByIdTicket(idTicket);
        //then
        assertThat(responseDto).isEqualTo(ResultAnnouncerResponseDto.builder()
                .isWinner(true)
                .ticketId("001")
                .numbersFromUser(Set.of(1, 2, 3, 4, 5, 6))
                .winnersNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .drawDate(LocalDateTime.now(clockForTest))
                .message("You win")
                .build());
    }

    @Test
    void should_return_object_from_cached_repository() {
        //given
        when(resultCheckerFacadeMock.retrieveTicketCheckedByIdTicket(idTicket)).thenReturn(
                TicketCheckedDto.builder()
                        .ticketId("001")
                        .winnersNumbers(Set.of(1, 2, 3, 4, 5, 6))
                        .numbersFromUser(Set.of(1, 2, 3, 4, 5, 6))
                        .drawDate(LocalDateTime.now(clockForTest))
                        .message("You win")
                        .isWinner(true)
                        .build()
        );
        TicketCheckedDto expectedTickeCheckedtDto = TicketCheckedDto.builder()
                .ticketId("001")
                .winnersNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .numbersFromUser(Set.of(1, 2, 3, 4, 5, 6))
                .drawDate(LocalDateTime.now(clockForTest))
                .message("You win")
                .isWinner(true)
                .build();
        //when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.generateResponseByIdTicket(
                idTicket);
        ResultAnnouncerResponseDto resultAnnouncerResponseDtoSecond = resultAnnouncerFacade.generateResponseByIdTicket(
                idTicket);
        //then
        assertThat(repository.findResponseById(idTicket)).isNotNull();
        assertThat(repository.findResponseById(idTicket)).isEqualTo(ResultAnnouncerMapper.mapToResultAnnouncerResponse(
                expectedTickeCheckedtDto));
    }

    @Test
    void should_return_object_from_result_checker_facade_when_not_exist_in_cached_repository() {
        //given
        when(resultCheckerFacadeMock.retrieveTicketCheckedByIdTicket(idTicket)).thenReturn(
                TicketCheckedDto.builder()
                        .ticketId("001")
                        .winnersNumbers(Set.of(1, 2, 3, 4, 5, 6))
                        .numbersFromUser(Set.of(1, 2, 3, 4, 5, 6))
                        .drawDate(LocalDateTime.now(clockForTest))
                        .message("You win")
                        .isWinner(true)
                        .build()
        );
        TicketCheckedDto expectedTickeCheckedtDto = TicketCheckedDto.builder()
                .ticketId("001")
                .winnersNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .numbersFromUser(Set.of(1, 2, 3, 4, 5, 6))
                .drawDate(LocalDateTime.now(clockForTest))
                .message("You win")
                .isWinner(true)
                .build();
        //when
        //then
        assertThat(repository.findResponseById(idTicket)).isNull();
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.generateResponseByIdTicket(
                idTicket);
        assertThat(repository.findResponseById(idTicket)).isEqualTo(ResultAnnouncerMapper.mapToResultAnnouncerResponse(
                expectedTickeCheckedtDto));
    }
}