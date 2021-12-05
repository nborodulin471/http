package nasa;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {

        CloseableHttpClient httpClient = getHttpClient();
        HttpGet request = new HttpGet("https://api.nasa.gov/planetary/apod?api_key=9wnYJvPbLLrWodHJiJRCEpnAcfDyg0BizTnHQ2hG");

        try (CloseableHttpResponse responseNASA = httpClient.execute(request)) {

            String body = new String(responseNASA.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            URLContent urlContent = gson.fromJson(body, URLContent.class);
            CloseableHttpResponse responsePlanetary = httpClient.execute(new HttpGet(urlContent.getUrl()));

            saveFile(urlContent, responsePlanetary.getEntity().getContent().readAllBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void saveFile(URLContent urlContent, byte[] content) {

        try (FileOutputStream stream = new FileOutputStream(urlContent.getNameContent())) {
            stream.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static CloseableHttpClient getHttpClient() {
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
    }
}
