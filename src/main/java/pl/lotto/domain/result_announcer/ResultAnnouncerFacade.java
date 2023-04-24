package pl.lotto.domain.result_announcer;

import lombok.AllArgsConstructor;
import pl.lotto.domain.result_announcer.dto.ResultAnnouncerResponseDto;
import pl.lotto.domain.result_checker.ResultCheckerFacade;
import pl.lotto.domain.result_checker.dto.TicketCheckedDto;

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