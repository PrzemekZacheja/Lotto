package pl.lotto.security;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.lotto.BaseIntegrationTest;
import pl.lotto.domain.loginandregister.dto.ResultRegistrationDto;
import pl.lotto.domain.numbersgenerator.NumbersGeneratorFacade;
import pl.lotto.infrastucture.loginandregister.dto.TokenResponseDto;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("security-integration")
public class UserSecurityIntegrationTest extends BaseIntegrationTest {

    @Autowired NumbersGeneratorFacade numbersGeneratorFacade;

    @Test
    void should_register_user_and_login() throws Exception {


        //1. User tries to obtain JWT Token via POST /token with email=someUser and password=somePassword and system
        // returned
        // UNAUTHORIZED (401)
        //given & when
        ResultActions failedLoginRequest = mockMvc.perform(post("/token").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                                         .content("""
                                                                                  {
                                                                                      "email": "someUser",
                                                                                      "password": "somePassword"
                                                                                  }
                                                                                  """.trim()));
        //then
        failedLoginRequest.andExpect(status().isUnauthorized())
                          .andExpect(content().json("""
                                                    {
                                                            "error": "UNAUTHORIZED",
                                                            "message": "Bad credentials"}
                                                        """.trim()));


        //2. User made GET /results/{ticketId} request and system returned FORBIDDEN (403)
        //given & when
        ResultActions failedResultsRequest =
                mockMvc.perform(get("/results/1").contentType(MediaType.APPLICATION_JSON_VALUE));
        //then
        failedResultsRequest.andExpect(status().isForbidden());

        //3. User made POST /register request with email=someUser and password=somePassword and system returned
        // CREATED (201)
        //given & when
        ResultActions registerRequest = mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                                         .content("""
                                                                                  {
                                                                                      "email": "someUser",
                                                                                      "password": "somePassword"
                                                                                  }
                                                                                  """.trim()));
        //then
        MvcResult mvcResult = registerRequest.andExpect(status().isCreated())
                                             .andReturn();
        String jwtToken = mvcResult.getResponse()
                                   .getContentAsString();
        ResultRegistrationDto resultRegistrationDto = objectMapper.readValue(jwtToken, ResultRegistrationDto.class);
        assertAll(() -> org.junit.jupiter.api.Assertions.assertEquals("someUser", resultRegistrationDto.email()),
                  () -> Assertions.assertThat(resultRegistrationDto.isLogged())
                                  .isFalse());

        //4. User made POST /token with email=someUser and password=somePassword and system returned JWT Token
        //given & when
        ResultActions successLoginRequest = mockMvc.perform(post("/token").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                                          .content("""
                                                                                   {
                                                                                       "email": "someUser",
                                                                                       "password": "somePassword"
                                                                                   }
                                                                                   """.trim()));
        //then
        ResultActions successResultToken = successLoginRequest.andExpect(status().isOk());
        String contentAsStringToken = successResultToken.andReturn()
                                                        .getResponse()
                                                        .getContentAsString();
        TokenResponseDto tokenResponseDto = objectMapper.readValue(contentAsStringToken, TokenResponseDto.class);
        Assertions.assertThat(tokenResponseDto.email())
                  .isEqualTo("someUser");
        String token = tokenResponseDto.token();
        Assertions.assertThat(token)
                  .matches(Pattern.compile("^([A-Za-z0-9-_=]+\\.)+([A-Za-z0-9-_=]+\\.?$)"));

    }
}