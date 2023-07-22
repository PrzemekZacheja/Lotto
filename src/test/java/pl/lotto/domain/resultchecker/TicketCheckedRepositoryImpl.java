package pl.lotto.domain.resultchecker;

import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class TicketCheckedRepositoryImpl implements TicketCheckedRepository {

    private final Map<String, TicketChecked> inMemoryDatabase = new ConcurrentHashMap<>();

    @Override
    public List<TicketChecked> findAllTicketCheckedByDate(LocalDateTime dateTime) {
        return inMemoryDatabase.values()
                               .stream()
                               .filter(ticketChecked -> ticketChecked.drawDate()
                                                                     .isEqual(dateTime))
                               .collect(Collectors.toList());
    }

    @Override
    public List<TicketChecked> saveAll(final List<TicketChecked> ticketCheckedDtoList) {

        ticketCheckedDtoList
                .forEach(ticketChecked -> inMemoryDatabase.put(ticketChecked.ticketId(), ticketChecked));
        return ticketCheckedDtoList;
    }

    @Override
    public TicketChecked findTicketById(String idTicket) {
        return inMemoryDatabase.get(idTicket);
    }


}