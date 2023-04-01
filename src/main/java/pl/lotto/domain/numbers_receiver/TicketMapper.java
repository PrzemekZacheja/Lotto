package pl.lotto.domain.numbers_receiver;

import pl.lotto.domain.numbers_receiver.dto.TicketDto;

class TicketMapper {

     public static TicketDto mapFromTicket(Ticket ticket) {
        return TicketDto.builder()
                .numbersFromUser(ticket.numbers())
                .ticketId(ticket.ticketId())
                .drawDate(ticket.drawDate())
                .build();
    }
}