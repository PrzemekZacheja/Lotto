package pl.lotto.infrastucture.loginandregister.controller;

import lombok.Builder;

@Builder
public record JwtResponseDto(String email,
                             String token) {
}