package pl.lotto.domain.resultannouncer;

import lombok.AllArgsConstructor;
import pl.lotto.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;
import pl.lotto.domain.resultchecker.dto.TicketCheckedDto;

@AllArgsConstructor
public class ResultAnnouncerFacade {

    ResultCheckerFacade resultCheckerFacade;
    ResultAnnouncerResponseRepository repository;

    public ResultAnnouncerResponseDto generateResponseByIdTicket(String idTicket) {
        TicketCheckedDto ticketCheckedDto = resultCheckerFacade.retrieveTicketCheckedByIdTicket(idTicket);
        if (ticketCheckedDto != null) {
            ResultAnnouncerResponse resultResponse = ResultAnnouncerMapper.mapToResultAnnouncerResponse(ticketCheckedDto);
            repository.save(resultResponse);
            return ResultAnnouncerMapper.mapToResultAnnouncerResponseDto(resultResponse);
        } else {
            return new ResultAnnouncerResponseDto(null, "Ticket id: " + idTicket + " not found");
        }
    }


}