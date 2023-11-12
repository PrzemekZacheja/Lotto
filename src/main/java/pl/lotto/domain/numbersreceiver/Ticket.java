package pl.lotto.domain.numbersreceiver;

import lombok.Builder;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
record Ticket(@Id String ticketId,
              LocalDateTime drawDate,
              Set<Integer> numbers) {

}