package pl.lotto.domain.numbersreceiver;

import org.springframework.data.mongodb.repository.*;

import java.time.*;
import java.util.*;

public interface NumberReceiverRepository extends MongoRepository<Ticket, String> {


    List<Ticket> findAllTicketsByDrawDate(LocalDateTime dateOfDraw);


}