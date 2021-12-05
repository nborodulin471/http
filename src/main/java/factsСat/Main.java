package factsСat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        CloseableHttpClient httpClient = gethttpClient();
        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
            List<FactsСat> factsСats = parseJson1(body);
            factsСats.stream().filter(value -> value.getUpvotes() != null && value.getUpvotes() > 0).forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static CloseableHttpClient gethttpClient() {
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
    }

    private static List<FactsСat> parseJson1(String value) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<FactsСat> result = mapper.readValue(value, new TypeReference<List<FactsСat>>() {
        });
        return result;
    }

}
