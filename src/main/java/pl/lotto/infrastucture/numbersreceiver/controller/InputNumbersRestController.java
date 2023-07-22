package pl.lotto.infrastucture.numbersreceiver.controller;

import lombok.*;
import lombok.extern.log4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pl.lotto.domain.numbersreceiver.*;
import pl.lotto.domain.numbersreceiver.dto.*;

import java.util.*;

@RestController
@Log4j2
@AllArgsConstructor
public class InputNumbersRestController {

    private final NumberReceiverFacade numberReceiverFacade;

    @PostMapping("/inputNumbers")
    public ResponseEntity<TicketDto> inputNumbers(
            @RequestBody InputNumbersRestDto requestDto
                                                 ) {
        Set<Integer> inputNumbers = new HashSet<>(requestDto.inputNumbers());
        TicketDto ticketDto = numberReceiverFacade.inputNumbers(inputNumbers);
        return ResponseEntity.ok(ticketDto);
    }
}