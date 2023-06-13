package pl.lotto.domain.numbersreceiver;

import pl.lotto.domain.numbersreceiver.dto.TicketDto;

class TicketMapper {

    private TicketMapper() {
    }

    public static TicketDto mapFromTicket(Ticket ticket) {
        return TicketDto.builder()
                        .numbersFromUser(ticket.numbers())
                        .ticketId(ticket.ticketId())
                        .drawDate(ticket.drawDate())
                        .build();
    }
}