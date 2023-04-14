package pl.lotto.domain.result_announcer.dto;

import lombok.Builder;

@Builder
public record ResultAnnouncerResponseDto(
        TicketInAnnounserDto
        String message
) {
}