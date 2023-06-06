package pl.lotto.infrastucture.numbersgenerator.http;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.lotto.domain.numbersgenerator.WinningNumberGenerable;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class WinningNumberGeneratorRestTemplate implements WinningNumberGenerable {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public Set<Integer> generateWinningRandomNumbers() {
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

        return Objects.requireNonNull(responseEntity.getBody()).stream().limit(6).collect(Collectors.toSet());
    }

    private String getUrlForService() {
        return uri + ":" + port + "/api/v1.0/random";
    }
}