package pl.lotto.infrastucture.numbersreceiver.controller;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public record InputNumbersRestDto(
        @NotNull(message = "{inputNumbers.notNull}")
        @NotEmpty(message = "{inputNumbers.notEmpty}")
        List<Integer> inputNumbers) {

}