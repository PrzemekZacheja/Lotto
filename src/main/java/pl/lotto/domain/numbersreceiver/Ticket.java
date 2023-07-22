package pl.lotto.domain.numbersreceiver;

import lombok.*;

import java.time.*;
import java.util.*;

@Builder
record Ticket(String ticketId, LocalDateTime drawDate, Set<Integer> numbers) {

}