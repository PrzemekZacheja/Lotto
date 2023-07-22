package pl.lotto.domain.resultchecker;

import lombok.*;
import pl.lotto.domain.drawdategenerator.*;
import pl.lotto.domain.numbersgenerator.*;
import pl.lotto.domain.numbersreceiver.*;
import pl.lotto.domain.resultchecker.dto.*;

import java.time.*;
import java.util.*;

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