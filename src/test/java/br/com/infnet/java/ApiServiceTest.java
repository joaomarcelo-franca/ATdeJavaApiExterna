package br.com.infnet.java;

import br.com.infnet.java.model.ApiModel;
import br.com.infnet.java.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
public class ApiServiceTest extends ApiService {
    @Autowired
    ApiService apiService;

    @Test
    void getApi_respostaBemSucedida() {

        ApiService apiService = new ApiServiceTest() {
            @Override
            public ApiModel getApi(String cep) {
                log.info("Simulacao de resposta bem sucedida da API");

                ApiModel apiModelTeste = new ApiModel();
                apiModelTeste.setState("RJ");
                apiModelTeste.setNeighborhood("Tanque");

                return apiModelTeste;
            }
        };

        ApiModel apiModel = apiService.getApi("22790671");

        assertNotNull(apiModel);
        assertEquals("RJ", apiModel.getState());
        assertEquals("Tanque", apiModel.getNeighborhood());
    }

    @Test
    void getApi_respostaDeErro() {
        ApiService apiService = new ApiServiceTest() {
            @Override
            public ApiModel getApi(String cep) {
                log.info("Simulacao de resposta de erro da API");
                throw new RuntimeException("Erro de Simulacao de API");
            }
        };

        RuntimeException excecao = assertThrows(RuntimeException.class, () -> apiService.getApi("12345678"));

        assertEquals("Erro de Simulacao de API", excecao.getMessage());

//        Erro Simlado
        String UFTeste = apiService.getApi("22790671").getState();
        assertNotEquals(UFTeste,"RJ");
    }
}
