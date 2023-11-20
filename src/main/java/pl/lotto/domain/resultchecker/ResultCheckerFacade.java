package pl.lotto.domain.resultchecker;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;
import pl.lotto.domain.numbersreceiver.NumberReceiverFacade;
import pl.lotto.domain.numbersreceiver.dto.TicketDto;
import pl.lotto.domain.resultchecker.dto.ResultDto;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Log4j2
public class ResultCheckerFacade {

    private final NumberReceiverFacade numberReceiverFacade;
    private final DrawDateFacade drawDateFacade;
    private final ResultRepository repository;
    private final ResultChecker resultChecker;


    public List<ResultDto> generateResultsOfTickets() {
        LocalDateTime dateOfNextDraw = drawDateFacade.generateDateOfNextDraw();
        log.info(dateOfNextDraw + " date of Next Draw");
        List<TicketDto> ticketDtoToCheck = numberReceiverFacade.retrieveAllTicketsDtoByDrawDate();
        return resultChecker.saveCheckedTicketsToResults(ticketDtoToCheck);
    }

    public ResultDto retrieveTicketCheckedByIdTicket(String idTicket) {
        return repository.findByTicketId(idTicket)
                         .map(ResultCheckerMapper::mapToTicketCheckedDto)
                         .orElseThrow(() -> new ResultCheckerNotFoundException("Not found for id: " + idTicket));
    }
}