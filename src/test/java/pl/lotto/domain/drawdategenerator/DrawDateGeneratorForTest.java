package pl.lotto.domain.drawdategenerator;

import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;

@AllArgsConstructor
public class DrawDateGeneratorForTest implements DrawDateGenerable {

    private final Clock clock;

    @Override
    public LocalDateTime generateNextDateOfDraw() {
        return LocalDateTime.now(clock);
    }

}