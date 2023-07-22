package pl.lotto.infrastucture.numbersgenerator.http;

import lombok.*;
import org.springframework.core.*;
import org.springframework.http.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;
import pl.lotto.domain.numbersgenerator.*;

import java.util.*;
import java.util.stream.*;

@AllArgsConstructor
public class WinningNumberGeneratorRestTemplate implements WinningNumberGenerable {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;


    private String getUrlForService() {
        return uri + ":" + port + "/api/v1.0/random";
    }

    @Override
    public Set<Integer> generateWinningRandomNumbers(final int lowerBand, final int upperBand, final int count) {
        String urlForService = getUrlForService();
        final String url = UriComponentsBuilder.fromHttpUrl(urlForService)
                                               .queryParam("min", 1)
                                               .queryParam("max", 99)
                                               .queryParam("count", 25)
                                               .toUriString();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<Integer>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(responseEntity.getBody())
                      .stream()
                      .limit(6)
                      .collect(Collectors.toSet());
    }
}