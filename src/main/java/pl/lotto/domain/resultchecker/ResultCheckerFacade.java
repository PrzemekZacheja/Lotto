package pl.lotto.domain.resultchecker;

import lombok.AllArgsConstructor;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;
import pl.lotto.domain.numbersgenerator.NumbersGeneratorFacade;
import pl.lotto.domain.numbersgenerator.dto.WinnerNumbersDto;
import pl.lotto.domain.numbersreceiver.NumberReceiverFacade;
import pl.lotto.domain.numbersreceiver.dto.TicketDto;
import pl.lotto.domain.resultchecker.dto.ResultDto;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class ResultCheckerFacade {

    private final NumbersGeneratorFacade numbersGenerator;
    private final NumberReceiverFacade numberReceiverFacade;
    private final DrawDateFacade drawDateFacade;
    private final ResultRepository repository;
    private final ResultChecker resultChecker;


    public List<ResultDto> generateResultsOfTickets() {
        LocalDateTime dateOfNextDraw = drawDateFacade.getDateOfNextDraw();
        List<TicketDto> ticketDtoToCheck = numberReceiverFacade.retrieveAllTicketsByNextDrawDate(dateOfNextDraw);
        WinnerNumbersDto winnerNumbersDto = numbersGenerator.retrieveAllWinnerNumbersByNextDrawDate(dateOfNextDraw);
        return resultChecker.checkWinnerResults(ticketDtoToCheck,
                                                winnerNumbersDto);
    }

    public ResultDto retrieveTicketCheckedByIdTicket(String idTicket) {
        return repository.findById(idTicket)
                         .map(ResultCheckerMapper::mapToTicketCheckedDto)
                         .orElseThrow(() -> new ResultCheckerNotFoundException("Not found for id: " + idTicket));
    }
}