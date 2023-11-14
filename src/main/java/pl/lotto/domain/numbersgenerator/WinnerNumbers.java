package pl.lotto.domain.numbersgenerator;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Document
record WinnerNumbers(
        LocalDateTime drawDate,
        Set<Integer> winningNumbers
) {

}