package pl.lotto.domain.numbersgenerator;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.*;
import java.util.*;

@Builder
@Document
record WinnerNumbers(
        @Id
        String id,
        LocalDateTime drawDate,
        Set<Integer> winningNumbers
) {

}