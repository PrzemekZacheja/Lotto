package pl.lotto.domain.drawdategenerator;

import java.time.*;

public interface DrawDateGenerable {

    LocalDateTime generateNextDateOfDraw();
}