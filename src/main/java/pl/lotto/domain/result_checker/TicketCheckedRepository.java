package pl.lotto.domain.result_checker;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketCheckedRepository {

    List<TicketChecked> findAllTicketCheckedByDate(LocalDateTime localDateTime);

    List<TicketChecked> saveAll(List<TicketChecked> ticketCheckedDtoList);

    TicketChecked findTicketById(String idTicket);
}