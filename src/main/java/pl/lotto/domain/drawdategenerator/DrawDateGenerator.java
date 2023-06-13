package pl.lotto.domain.drawdategenerator;

import lombok.AllArgsConstructor;

import java.time.*;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

@AllArgsConstructor
class DrawDateGenerator implements DrawDateGenerable {

    public static final LocalTime HOUR_OF_DRAW = LocalTime.of(12, 0, 0);
    public static final TemporalAdjuster NEXT_SATURDAY = TemporalAdjusters.next(DayOfWeek.SATURDAY);
    private final Clock clock;

    private static boolean isSundayAndBeforeNoon(final LocalDateTime dateOfTicket) {
        return dateOfTicket.getDayOfWeek()
                           .equals(DayOfWeek.SUNDAY) && dateOfTicket.toLocalTime()
                                                                    .isBefore(HOUR_OF_DRAW);
    }

    @Override
    public LocalDateTime generateNextDateOfDraw() {
        LocalDateTime dateOfTicket = LocalDateTime.now(clock);
        if (isSundayAndBeforeNoon(dateOfTicket)) {
            return LocalDateTime.of(dateOfTicket.toLocalDate(), HOUR_OF_DRAW);
        }
        LocalDate nextSaturdayFromDateOfTicket = dateOfTicket.toLocalDate()
                                                             .with(NEXT_SATURDAY);
        return LocalDateTime.of(nextSaturdayFromDateOfTicket, HOUR_OF_DRAW);
    }
}