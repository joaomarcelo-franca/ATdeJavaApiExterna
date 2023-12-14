package br.com.infnet.java.service;

import br.com.infnet.java.model.ApiModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@Slf4j
@Service
public class ApiService {
    public ApiModel getApi(String cep) {
        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .version(HttpClient.Version.HTTP_2)
                    .uri(new URI("https://brasilapi.com.br/api/cep/v1/" + cep))
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
            ApiModel apiModel = mapper.readValue(httpResponse.body(), ApiModel.class);
            log.info("Brasil Api Cep Consumida");
            return apiModel;
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            log.error("Error de Api: " + ex.getMessage());
            throw new RuntimeException("Erro:" + ex.getMessage());
        }
    }
}
