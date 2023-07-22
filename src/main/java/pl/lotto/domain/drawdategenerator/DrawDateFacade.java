package pl.lotto.domain.drawdategenerator;

import lombok.*;

import java.time.*;

@AllArgsConstructor
public class DrawDateFacade {

    private final DrawDateGenerable dateDrawGenerator;

    public LocalDateTime getDateOfNextDraw() {
        return dateDrawGenerator.generateNextDateOfDraw();
    }
}