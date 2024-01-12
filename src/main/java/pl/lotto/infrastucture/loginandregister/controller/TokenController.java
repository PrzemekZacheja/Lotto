package pl.lotto.infrastucture.loginandregister.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.infrastucture.loginandregister.dto.TokenRequestDto;
import pl.lotto.infrastucture.loginandregister.dto.TokenResponseDto;
import pl.lotto.infrastucture.security.jwt.JwtAuthenticatorFacade;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class TokenController {

    private final JwtAuthenticatorFacade jwtAuthenticatorFacade;

    @PostMapping("/token")
    public ResponseEntity<JwtResponseDto> getToken(@Valid @RequestBody TokenRequestDto tokenRequestDto) {
        TokenResponseDto jwtResponseDto = jwtAuthenticatorFacade.authenticate(tokenRequestDto);
        return ResponseEntity.ok()
                             .body(JwtResponseDto.builder()
                                                 .email(jwtResponseDto.email())
                                                 .token(jwtResponseDto.token())
                                                 .build());
    }
}