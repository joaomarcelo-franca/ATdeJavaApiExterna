package br.com.infnet.java;

import br.com.infnet.java.exceptions.IdErrorException;
import br.com.infnet.java.exceptions.NomeErrorException;
import br.com.infnet.java.model.AlunoModel;
import br.com.infnet.java.model.ApiModel;
import br.com.infnet.java.service.AlunoService;


import br.com.infnet.java.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class AlunoServiceTest {

    @Autowired
    private AlunoService alunoService;
    private SimpleApiService simpleApiService;


    @BeforeEach
    void setUp() {
        simpleApiService = new SimpleApiService();
        alunoService = new AlunoService();
        alunoService.setApiService(simpleApiService);
    }

    @Test
    void getAll() {
        List<AlunoModel> alunos = alunoService.getAll();
        assertNotNull(alunos);
        assertEquals(2, alunos.size());
        log.info("Teste getAll executado com sucesso");
    }

    @Test
    void getById_validId() {
        AlunoModel aluno = alunoService.getById(1);
        assertNotNull(aluno);
        assertEquals(1, aluno.getId());
        log.info("Teste getById_validId executado com sucesso.");
    }

    @Test
    void getById_invalidId() {
        assertThrows(IdErrorException.class, () -> alunoService.getById(100));
        log.info("Teste getById_invalidId executado com sucesso.");
    }

    @Test
    void getByNome_existingNome() {
        // Mockando dados
        List<AlunoModel> mockAlunos = List.of(new AlunoModel(1, "cep1", "state1", "neighborhood1", "Joao", new String[]{"Medicina"}));
        ApiModel apiModel1 = new ApiModel();
        apiModel1.setState("state1");
        apiModel1.setNeighborhood("neighborhood1");
        SimpleApiService.apiModels.put("22790671", apiModel1);

        ApiModel apiModel2 = new ApiModel();
        apiModel2.setState("state2");
        apiModel2.setNeighborhood("neighborhood2");
        SimpleApiService.apiModels.put("22783117", apiModel2);

        assertEquals(mockAlunos, alunoService.getByNome("Joao"));
        log.info("Teste getByNome_existingNome executado com sucesso.");
    }

    @Test
    void getByNome_nonExistingNome() {
        assertThrows(NomeErrorException.class, () -> alunoService.getByNome("NomeInexistente"));
        log.info("Teste getByNome_nonExistingNome executado com sucesso.");
    }

    @Test
    void deleteById_validId() {
        alunoService.deleteById(1);
        assertTrue(simpleApiService.deleteCalledWithId == 1);
        log.info("Teste deleteById_validId executado com sucesso.");
    }

    @Test
    void deleteById_invalidId() {
        assertThrows(IdErrorException.class, () -> alunoService.deleteById(100));
        log.info("Teste deleteById_invalidId executado com sucesso.");
    }

    @Test
    void updateById() {
        AlunoModel alunoAtualizado = new AlunoModel(1, "novoCep", "novoEstado", "novoBairro", "novoNome", new String[]{"novoCurso"});
        alunoService.updateById(1, alunoAtualizado);
        assertTrue(simpleApiService.updateCalledWithId == 1);
        assertEquals(alunoAtualizado, simpleApiService.updatedAluno);
        log.info("Teste updateById executado com sucesso.");
    }

    @Test
    void post() {
        AlunoModel novoAluno = new AlunoModel(0, "novoCep", "novoEstado", "novoBairro", "novoNome", new String[]{"novoCurso"});
        alunoService.post(novoAluno);
        assertEquals(novoAluno, simpleApiService.postCalledWithAluno);
        log.info("Teste post executado com sucesso.");
    }

    private static class SimpleApiService extends ApiService {
        static Map<String, ApiModel> apiModels = new HashMap<>();
        int deleteCalledWithId = -1;
        int updateCalledWithId = -1;
        AlunoModel updatedAluno;
        AlunoModel postCalledWithAluno;


        public void deleteById(int id) {
            deleteCalledWithId = id;
        }


        public void updateById(int id, AlunoModel alunoModel) {
            updateCalledWithId = id;
            updatedAluno = alunoModel;
        }


        public void post(AlunoModel alunoModel) {
            postCalledWithAluno = alunoModel;
        }

        public ApiModel getApi(String cep) {
            return apiModels.get(cep);
        }
    }
}
