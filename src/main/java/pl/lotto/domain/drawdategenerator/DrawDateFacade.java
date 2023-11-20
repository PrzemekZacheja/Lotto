package pl.lotto.domain.drawdategenerator;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class DrawDateFacade {

    private final DrawDateGenerable dateDrawGenerator;

    public LocalDateTime generateDateOfNextDraw() {
        return dateDrawGenerator.generateNextDateOfDraw();
    }
}