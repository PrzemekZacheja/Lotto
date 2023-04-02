package pl.lotto.domain.numbers_receiver;

import lombok.AllArgsConstructor;
import pl.lotto.domain.numbers_receiver.dto.InputNumbersResultDto;
import pl.lotto.domain.numbers_receiver.dto.TicketDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class NumberReceiverFacade {

    private final NumberValidator validator;
    private final NumberReceiverRepository repository;
    private final Clock clock;
    private final HashGenerator hashGenerator;
    private final DateDrawGenerator dateDrawGenerator;

    public InputNumbersResultDto inputNumbers(Set<Integer> numbers) {
        boolean areAllNumbersInRange = validator.areAllNumbersInRange(numbers);
        if (areAllNumbersInRange) {
            String ticketId = hashGenerator.getHash();
            LocalDateTime drawDate = dateDrawGenerator.generateNextDateOfDraw();
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

    public List<TicketDto> usersNumbers(LocalDateTime dateOfDraw) {
        List<Ticket> allTicketsByDrawDate = repository.findAllTicketsByDrawDate(dateOfDraw);
        return allTicketsByDrawDate.stream()
                .map(TicketMapper::mapFromTicket).toList();
    }

    public List<TicketDto> retrieveAllTicketsByNextDrawDate( LocalDateTime drawDate) {
        LocalDateTime nextDateOfDraw = dateDrawGenerator.generateNextDateOfDraw();
        if (drawDate.isAfter(nextDateOfDraw)){
            return Collections.emptyList();
        }
        return repository.findAllTicketsByDrawDate(drawDate)
                .stream()
                .map(TicketMapper::mapFromTicket)
                .toList();
    }

    public LocalDateTime getDrawDate() {
        return dateDrawGenerator.generateNextDateOfDraw();
    }
}