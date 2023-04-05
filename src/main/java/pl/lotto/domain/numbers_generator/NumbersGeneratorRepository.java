package pl.lotto.domain.numbers_generator;

import java.time.LocalDateTime;

public interface NumbersGeneratorRepository {

    WinnerNumbers save(WinnerNumbers winnerNumbers);

    WinnerNumbers findWinnerNumbersByDrawDate(LocalDateTime localDateTime);
}