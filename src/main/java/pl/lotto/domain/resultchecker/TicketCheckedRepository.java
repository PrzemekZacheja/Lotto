package pl.lotto.domain.resultchecker;

import java.time.*;
import java.util.*;

public interface TicketCheckedRepository {

    List<TicketChecked> findAllTicketCheckedByDate(LocalDateTime localDateTime);

    List<TicketChecked> saveAll(List<TicketChecked> ticketCheckedDtoList);

    TicketChecked findTicketById(String idTicket);
}