package pl.lotto.infrastucture.numbersreceiver.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.domain.numbersreceiver.NumberReceiverFacade;
import pl.lotto.domain.numbersreceiver.dto.InputNumbersResultDto;

import java.util.Set;

@RestController
@Log4j2
@AllArgsConstructor
public class InputNumbersRestController {

    private final NumberReceiverFacade numberReceiverFacade;

    @PostMapping("/inputNumbers")
    public ResponseEntity<InputNumbersResultDto> inputNumbers(@RequestBody InputNumbersRestDto requestDto) {
        Set<Integer> inputNumbers = requestDto.inputNumbers();
        InputNumbersResultDto inputNumbersResultDto = numberReceiverFacade.inputNumbers(inputNumbers);
        return ResponseEntity.ok(inputNumbersResultDto);
    }
}