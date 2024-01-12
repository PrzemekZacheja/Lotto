package pl.lotto.infrastucture.loginandregister.dto;

import lombok.Builder;

@Builder
public record TokenResponseDto(
        String email,
        String token
) {
}