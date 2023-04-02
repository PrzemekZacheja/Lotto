package pl.lotto.domain.numbers_generator;

import java.time.LocalDateTime;
import java.util.List;

public interface NumbersGeneratorRepository {

    WinnerNumbers save(WinnerNumbers winnerNumbers);

    List<WinnerNumbers> findAllWinnerNumbersByDrawDate(LocalDateTime localDateTime);
}