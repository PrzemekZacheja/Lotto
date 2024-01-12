package pl.lotto.domain.loginandregister;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import pl.lotto.domain.loginandregister.dto.UserDto;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class LoginAndRegisterFacadeTest {

    final LoginAndRegisterFacadeRepository repository = new LoginAndRegisterFacadeRepositoryForTest();
    final LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(repository);

    @Test
    void should_save_user_to_database_if_not_exist() {
        //given
        String email = "example@gov.pl";
        String password = "1234";
        //when
        UserDto registeredUser = loginAndRegisterFacade.registerUser(email, password);
        //then
        Optional<User> repositoryByEmail = repository.findByEmail(email);
        assertThat(registeredUser).isEqualTo(MapperLoginAndRegister.mapToUserDto(repositoryByEmail.get()));
    }

    @Test
    void should_create_User_and_set_isLogged_to_false_when_user_not_exist_in_repository() {
        //given
        String email = "example@gov.pl";
        String password = "1234";
        //when
        UserDto firstLogin = loginAndRegisterFacade.registerUser(email, password);
        //then
        assertThat(firstLogin.isLogged()).isFalse();
    }


    @Test
    void should_change_isLogged_to_true_when_user_exist_in_repository() {
        //given
        String email = "example@gov.pl";
        String password = "1234";
        //when
        UserDto firstLogin = loginAndRegisterFacade.registerUser(email, password);
        UserDto secondLogin = loginAndRegisterFacade.registerUser(email, password);
        //then
        assertThat(secondLogin.isLogged()).isTrue();
    }

    @Test
    void should_throw_exception_when_user_is_not_found() {
        //given
        String email = "example@gov.pl";
        //when
        //then
        assertThatThrownBy(() -> loginAndRegisterFacade.loginUser(email)).isInstanceOf(BadCredentialsException.class);
    }
}