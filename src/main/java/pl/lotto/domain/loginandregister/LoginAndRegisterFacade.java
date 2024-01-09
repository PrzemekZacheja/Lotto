package pl.lotto.domain.loginandregister;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import pl.lotto.domain.loginandregister.dto.UserDto;

@AllArgsConstructor
@Component
public class LoginAndRegisterFacade {

    private final LoginAndRegisterFacadeRepository repository;

    public UserDto registerUser(String email, String password) {
        boolean savedInRepository = isSavedInRepository(email);

        if (savedInRepository) {
            return loginUser(email);
        } else {
            User user = User
                    .builder()
                    .token(TokenGenerator.generateToken())
                    .email(email)
                    .isLogged(false)
                    .password(password)
                    .build();

            repository.save(user);
            return MapperLoginAndRegister.mapToUserDto(user);
        }
    }

    private boolean isSavedInRepository(String email) {
        return repository.findByEmail(email)
                         .isPresent();
    }

    public UserDto loginUser(String email) {
        User user = repository
                .findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("No user with email: " + email));
        user.loggIn();
        return MapperLoginAndRegister.mapToUserDto(user);
    }

    public UserDto findByEmail(String email) {
        User user = repository
                .findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException(
                        "No user with email: " + email));
        return MapperLoginAndRegister.mapToUserDto(user);
    }
}