package pl.lotto.domain.resultannouncer;

import lombok.*;
import pl.lotto.domain.resultannouncer.dto.*;
import pl.lotto.domain.resultchecker.*;
import pl.lotto.domain.resultchecker.dto.*;

@AllArgsConstructor
public class ResultAnnouncerFacade {

    ResultCheckerFacade resultCheckerFacade;
    ResultAnnouncerResponseRepository repository;

    public ResultAnnouncerResponseDto generateResponseByIdTicket(String idTicket) {
        TicketCheckedDto ticketCheckedDto = resultCheckerFacade.retrieveTicketCheckedByIdTicket(idTicket);
        if (repository.findResponseById(idTicket) == null) {
            ResultAnnouncerResponse resultResponse = ResultAnnouncerMapper.mapToResultAnnouncerResponse(ticketCheckedDto);
            repository.save(resultResponse);
            return ResultAnnouncerMapper.mapToResultAnnouncerResponseDto(resultResponse);
        } else {
            return ResultAnnouncerMapper.mapToResultAnnouncerResponseDto(repository.findResponseById(idTicket));
        }
    }


}