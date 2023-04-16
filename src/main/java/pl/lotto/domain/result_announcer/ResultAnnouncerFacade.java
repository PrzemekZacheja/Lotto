package pl.lotto.domain.result_announcer;

import lombok.AllArgsConstructor;
import pl.lotto.domain.result_announcer.dto.ResultAnnouncerResponseDto;
import pl.lotto.domain.result_checker.ResultCheckerFacade;
import pl.lotto.domain.result_checker.dto.TicketCheckedDto;

@AllArgsConstructor
public class ResultAnnouncerFacade {

    ResultCheckerFacade resultCheckerFacade;

    public ResultAnnouncerResponseDto generateResponseByIdTicket(String idTicket) {
        TicketCheckedDto ticketCheckedDto = resultCheckerFacade.retrieveTicketCheckedByIdTicket(idTicket);

        return ResultAnnouncerResponseDto.builder()
                .isWinner(ticketCheckedDto.isWinner())
                .ticketId(ticketCheckedDto.ticketId())
                .numbersFromUser(ticketCheckedDto.numbersFromUser())
                .winnersNumbers(ticketCheckedDto.winnersNumbers())
                .drawDate(ticketCheckedDto.drawDate())
                .message(ticketCheckedDto.message())
                .build();
    }
}