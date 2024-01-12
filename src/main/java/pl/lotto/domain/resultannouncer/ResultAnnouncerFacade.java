package pl.lotto.domain.resultannouncer;

import lombok.AllArgsConstructor;
import pl.lotto.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;
import pl.lotto.domain.resultchecker.dto.ResultDto;

@AllArgsConstructor
public class ResultAnnouncerFacade {

    ResultCheckerFacade resultCheckerFacade;
    ResultAnnouncerResponseRepository repository;

    public ResultAnnouncerResponseDto generateResponseByIdTicket(String idTicket) {
        if (repository.existsByTicketId(idTicket)) {
            ResultAnnouncerResponse resultResponse = repository.findByTicketId(idTicket);
            return ResultAnnouncerMapper.mapToResultAnnouncerResponseDto(resultResponse);
        }

        ResultDto resultDto = resultCheckerFacade.retrieveTicketCheckedByIdTicket(idTicket);
        if (resultDto != null) {
            ResultAnnouncerResponse resultResponse = ResultAnnouncerMapper.mapToResultAnnouncerResponse(resultDto);
            repository.save(resultResponse);
            return ResultAnnouncerMapper.mapToResultAnnouncerResponseDto(resultResponse);
        } else {
            return new ResultAnnouncerResponseDto(null, "Ticket id: " + idTicket + " not found");
        }
    }


}