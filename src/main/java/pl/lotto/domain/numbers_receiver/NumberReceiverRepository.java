package pl.lotto.domain.numbers_receiver;

import java.time.LocalDateTime;
import java.util.List;

public interface NumberReceiverRepository {

    Ticket save(Ticket ticket);

    List<Ticket> findAllTicketsByDrawDate(LocalDateTime dateOfDraw);


}