package pl.lotto.domain.numbersreceiver.dto;

import lombok.*;

import java.time.*;
import java.util.*;

@Builder
public record TicketDto(String message, String ticketId, LocalDateTime drawDate, Set<Integer> userNumbers) {

}