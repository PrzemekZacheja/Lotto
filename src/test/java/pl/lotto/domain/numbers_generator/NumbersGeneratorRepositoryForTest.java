package pl.lotto.domain.numbers_generator;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
class NumbersGeneratorRepositoryForTest implements NumbersGeneratorRepository {

    private final Map<LocalDateTime, WinnerNumbers> localDateTimeWinnerNumbersMap = new ConcurrentHashMap<>();

    @Override
    public WinnerNumbers save(WinnerNumbers winnerNumbers) {
        localDateTimeWinnerNumbersMap.put(winnerNumbers.timeOfWinDrawNumbers(), winnerNumbers);
        return winnerNumbers;
    }

    @Override
    public List<WinnerNumbers> findAllWinnerNumbersByDrawDate(LocalDateTime localDateTime) {
        return localDateTimeWinnerNumbersMap.values().stream()
                .filter(winnerNumbers -> winnerNumbers.timeOfWinDrawNumbers().isEqual(localDateTime))
                .toList();
    }
}