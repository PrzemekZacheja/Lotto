package pl.lotto.domain.drawdategenerator;

import lombok.*;

import java.time.*;

@AllArgsConstructor
public class DrawDateGeneratorForTest implements DrawDateGenerable {

    private final Clock clock;

    @Override
    public LocalDateTime generateNextDateOfDraw() {
        return LocalDateTime.now(clock);
    }

}