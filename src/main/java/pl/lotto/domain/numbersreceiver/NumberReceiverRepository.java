package pl.lotto.domain.numbersreceiver;

import java.time.LocalDateTime;
import java.util.List;

public interface NumberReceiverRepository {

    Ticket save(Ticket ticket);

    List<Ticket> findAllTicketsByDrawDate(LocalDateTime dateOfDraw);


}