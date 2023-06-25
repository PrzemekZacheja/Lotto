package pl.lotto.domain.numbersreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryNumberReceiverRepositoryImpl implements NumberReceiverRepository {

    Map<String, Ticket> inMemoryDatabase = new ConcurrentHashMap<>();

    @Override
    public Ticket save(final Ticket ticket) {
        inMemoryDatabase.put(ticket.ticketId(), ticket);
        return ticket;
    }

    @Override
    public List<Ticket> findAllTicketsByDrawDate(final LocalDateTime date) {
        return inMemoryDatabase.values()
                .stream()
                .filter(ticket -> ticket.drawDate()
                        .isEqual(date))
                .toList();
    }
}