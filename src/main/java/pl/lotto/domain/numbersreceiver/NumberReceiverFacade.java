package pl.lotto.domain.numbersreceiver;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;
import pl.lotto.domain.numbersreceiver.dto.TicketDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Log4j2
public class NumberReceiverFacade {

    private final NumberValidator validator;
    private final NumberReceiverRepository repository;
    private final HashGenerable hashGenerator;
    private final DrawDateFacade drawDateFacade;

    public TicketDto inputNumbers(Set<Integer> numbers) {
        boolean areAllNumbersInRange = validator.areAllNumbersInRange(numbers);
        if (areAllNumbersInRange) {
            String ticketId = hashGenerator.getHash();
            LocalDateTime drawDate = drawDateFacade.generateDateOfNextDraw();
            Ticket savedTicket = repository.save(new Ticket(ticketId, drawDate, numbers));
            log.info("Saved ticket: {}", savedTicket);
            return TicketDto.builder()
                            .ticketId(savedTicket.ticketId())
                            .drawDate(savedTicket.drawDate())
                            .message("Success")
                            .userNumbers(numbers)
                            .build();
        }
        return TicketDto.builder()
                        .message("Fail")
                        .build();
    }

    public List<TicketDto> usersNumbers(DrawDateFacade dateOfDraw) {
        List<Ticket> allTicketsByDrawDate = repository.findByDrawDate(dateOfDraw.getDateOfNextDraw());
        return allTicketsByDrawDate.stream()
                                   .map(TicketMapper::mapFromTicket)
                                   .toList();
    }

    public List<TicketDto> retrieveAllTicketsByNextDrawDate(LocalDateTime drawDate) {
        LocalDateTime nextDateOfDraw = drawDateFacade.getDateOfNextDraw();
        if (drawDate.isAfter(nextDateOfDraw)) {
            return Collections.emptyList();
        }
        return repository.findByDrawDate(drawDate)
                         .stream()
                         .map(TicketMapper::mapFromTicket)
                         .toList();
    }

    public LocalDateTime getDrawDate() {
        return drawDateFacade.getDateOfNextDraw();
    }
}