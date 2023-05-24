package pl.lotto.domain.numbersgenerator;

import java.time.LocalDateTime;

public interface NumbersGeneratorRepository {

    WinnerNumbers save(WinnerNumbers winnerNumbers);

    WinnerNumbers findWinnerNumbersByDrawDate(LocalDateTime localDateTime);
}