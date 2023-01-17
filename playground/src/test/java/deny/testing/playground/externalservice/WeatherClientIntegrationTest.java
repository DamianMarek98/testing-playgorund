package deny.testing.playground.externalservice;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import deny.testing.playground.client.WeatherClient;
import deny.testing.playground.dto.WeatherResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@WireMockTest(httpPort = 8089)
@SpringBootTest
class WeatherClientIntegrationTest {

    @Autowired
    private WeatherClient weatherClient;

    @Test
    void shouldCallWeatherService() {
        stubFor(get(urlPathEqualTo("/some-test-api-key/54.3489042285788,18.65315878830999"))
                .willReturn(aResponse()
                        .withBody("{\"status\": \"Rain\"}") // could be extracted to resource file
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)));

        var weatherResponse = weatherClient.fetchWeather();

        WeatherResponse expectedResponse = new WeatherResponse("Rain");
        assertEquals(expectedResponse, weatherResponse);
    }
}
