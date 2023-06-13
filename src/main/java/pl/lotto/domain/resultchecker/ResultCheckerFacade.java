package pl.lotto.domain.resultchecker;

import lombok.AllArgsConstructor;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;
import pl.lotto.domain.numbersgenerator.NumbersGeneratorFacade;
import pl.lotto.domain.numbersreceiver.NumberReceiverFacade;
import pl.lotto.domain.resultchecker.dto.TicketCheckedDto;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class ResultCheckerFacade {

    private final NumbersGeneratorFacade numbersGenerator;
    private final NumberReceiverFacade numberReceiverFacade;
    private final DrawDateFacade drawDateFacade;
    private final TicketCheckedRepository repository;
    private final ResultChecker resultChecker;


    List<TicketCheckedDto> generateWinningTickets() {
        LocalDateTime dateOfNextDraw = drawDateFacade.getDateOfNextDraw();

        return resultChecker.checkWinnerResults(numberReceiverFacade.retrieveAllTicketsByNextDrawDate(dateOfNextDraw),
                                                numbersGenerator.retrieveAllWinnerNumbersByNextDrawDate(dateOfNextDraw));
    }

    public List<TicketChecked> retrieveTicketCheckedByDate(LocalDateTime localDateTime) {
        return repository.findAllTicketCheckedByDate(localDateTime);
    }

    public TicketCheckedDto retrieveTicketCheckedByIdTicket(String idTicket) {
        TicketChecked ticketById = repository.findTicketById(idTicket);
        return ResultCheckerMapper.mapToTicketCheckedDto(ticketById);
    }
}