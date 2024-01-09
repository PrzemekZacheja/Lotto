package pl.lotto.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record ResultRegistrationDto(String email,
                                    String password,
                                    String token,
                                    boolean isLogged) {
}