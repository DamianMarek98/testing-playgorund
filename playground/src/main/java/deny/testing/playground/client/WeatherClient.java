package deny.testing.playground.client;

import deny.testing.playground.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherClient {

    public static final String GDANSK_COORDS = "54.3489042285788,18.65315878830999";
    private final WebClient webClient;

    @Autowired
    public WeatherClient(@Value("${weather.url}") final String weatherServiceUrl,
                         @Value("${weather.api_key}") final String weatherServiceApiKey,
                         WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(weatherServiceUrl + "/" + weatherServiceApiKey)
                .build();
    }

    public WeatherResponse fetchWeather() {
        return webClient
                .get()
                .uri("/" + GDANSK_COORDS)
                .retrieve()
                .bodyToMono(WeatherResponse.class)
                .block();
    }
}
