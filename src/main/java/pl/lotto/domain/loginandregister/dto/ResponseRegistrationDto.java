package pl.lotto.domain.loginandregister.dto;

import javax.validation.constraints.NotBlank;

public record ResponseRegistrationDto(
        @NotBlank(message = "{email.not.blank}")
        String email,
        @NotBlank(message = "{password.not.blank}")
        String password) {
}