package pl.lotto.apivalidationerror;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.lotto.BaseIntegrationTest;
import pl.lotto.infrastucture.apivalidation.ApiValidationErrorDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiValidationFailedIntegrationTest extends BaseIntegrationTest {

    @Test
    @WithMockUser
    void should_return_400_with_error_message_when_input_numbers_is_empty() throws Exception {
        //given
        //when
        ResultActions perform = mockMvc.perform(post("/inputNumbers").content("""
                                                                              {
                                                                              "inputNumbers" : []
                                                                              }
                                                                              """.trim())
                                                                     .contentType(MediaType.APPLICATION_JSON));
        //then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest())
                                     .andReturn();
        String contentAsString = mvcResult.getResponse()
                                          .getContentAsString();
        ApiValidationErrorDto apiValidationErrorDto = objectMapper.readValue(contentAsString, ApiValidationErrorDto.class);
        Assertions.assertThat(apiValidationErrorDto.messages())
                  .containsExactlyInAnyOrder("input numbers must not be empty");
    }

    @Test
    @WithMockUser
    void should_return_400_with_error_message_when_json_is_empty() throws Exception {
        //given
        //when
        ResultActions perform = mockMvc.perform(post("/inputNumbers").content("""
                                                                              {}
                                                                              """.trim())
                                                                     .contentType(MediaType.APPLICATION_JSON));
        //then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest())
                                     .andReturn();
        String contentAsString = mvcResult.getResponse()
                                          .getContentAsString();
        ApiValidationErrorDto apiValidationErrorDto = objectMapper.readValue(contentAsString, ApiValidationErrorDto.class);
        Assertions.assertThat(apiValidationErrorDto.messages())
                  .containsExactlyInAnyOrder("input numbers must not be null", "input numbers must not be empty");
    }

}