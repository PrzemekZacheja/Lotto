package pl.lotto.domain.numbersreceiver;

import lombok.AllArgsConstructor;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;
import pl.lotto.domain.numbersreceiver.dto.InputNumbersResultDto;
import pl.lotto.domain.numbersreceiver.dto.TicketDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class NumberReceiverFacade {

    private final NumberValidator validator;
    private final NumberReceiverRepository repository;
    private final HashGenerable hashGenerator;
    private final DrawDateFacade drawDateFacade;

    public InputNumbersResultDto inputNumbers(Set<Integer> numbers) {
        boolean areAllNumbersInRange = validator.areAllNumbersInRange(numbers);
        if (areAllNumbersInRange) {
            String ticketId = hashGenerator.getHash();
            LocalDateTime drawDate = drawDateFacade.getDateOfNextDraw();
            Ticket savedTicket = repository.save(new Ticket(ticketId, drawDate, numbers));
            return InputNumbersResultDto.builder()
                    .ticketId(savedTicket.ticketId())
                    .drawDate(savedTicket.drawDate())
                    .message("Success")
                    .userNumbers(numbers)
                    .build();
        }
        return InputNumbersResultDto.builder()
                .message("Fail")
                .build();
    }

    public List<TicketDto> usersNumbers(DrawDateFacade dateOfDraw) {
        List<Ticket> allTicketsByDrawDate = repository.findAllTicketsByDrawDate(dateOfDraw.getDateOfNextDraw());
        return allTicketsByDrawDate.stream()
                .map(TicketMapper::mapFromTicket)
                .toList();
    }

    public List<TicketDto> retrieveAllTicketsByNextDrawDate(LocalDateTime drawDate) {
        LocalDateTime nextDateOfDraw = drawDateFacade.getDateOfNextDraw();
        if (drawDate.isAfter(nextDateOfDraw)) {
            return Collections.emptyList();
        }
        return repository.findAllTicketsByDrawDate(drawDate)
                .stream()
                .map(TicketMapper::mapFromTicket)
                .toList();
    }

    public LocalDateTime getDrawDate() {
        return drawDateFacade.getDateOfNextDraw();
    }
}