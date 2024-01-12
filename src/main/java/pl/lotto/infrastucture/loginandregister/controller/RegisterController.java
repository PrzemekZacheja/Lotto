package pl.lotto.infrastucture.loginandregister.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.domain.loginandregister.LoginAndRegisterFacade;
import pl.lotto.domain.loginandregister.dto.ResponseRegistrationDto;
import pl.lotto.domain.loginandregister.dto.ResultRegistrationDto;
import pl.lotto.domain.loginandregister.dto.UserDto;

@RestController
@AllArgsConstructor
public class RegisterController {

    private final LoginAndRegisterFacade loginAndRegisterFacade;
    private final PasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<ResultRegistrationDto> register(@RequestBody ResponseRegistrationDto responseRegistrationDto) {
        String encodedPassword = bCryptPasswordEncoder.encode(responseRegistrationDto.password());
        UserDto registeredUser = loginAndRegisterFacade.registerUser(
                responseRegistrationDto.email(),
                encodedPassword);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(
                                     ResultRegistrationDto
                                             .builder()
                                             .token(registeredUser.token())
                                             .isLogged(registeredUser.isLogged())
                                             .email(registeredUser.email())
                                             .password(encodedPassword)
                                             .build());
    }
}