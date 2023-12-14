package br.com.infnet.java.service;

import br.com.infnet.java.exceptions.IdErrorException;
import br.com.infnet.java.exceptions.NomeErrorException;
import br.com.infnet.java.model.AlunoModel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Data
public class AlunoService {
    private Map<Integer, AlunoModel> alunos = getAlunos();
    private Integer lastId = alunos.size();
    @Autowired
    ApiService apiService;

    private Map<Integer,AlunoModel> getAlunos(){
        Map<Integer, AlunoModel> alunos = new HashMap<>();
        String[] cursos = {"Medicina", "Direito", "Odonto"};
        ApiService apiService = new ApiService();
        String cep1 = "22790671";
        String cep2 = "22783117";
        AlunoModel aluno1 = new AlunoModel(1,cep1, apiService.getApi(cep1).getState(), apiService.getApi(cep1).getNeighborhood(), "Joao", cursos);
        AlunoModel aluno2 = new AlunoModel(2,cep2, apiService.getApi(cep2).getState(), apiService.getApi(cep2).getNeighborhood(),"Arroz", cursos);
        alunos.put(aluno1.getId(), aluno1);
        alunos.put(aluno2.getId(), aluno2);
        return alunos;
    }
    public List<AlunoModel> getAll() {
        return alunos.values().stream().toList();
    }

    public AlunoModel getById(int id) {
        return ValidaId(id);
    }

    public List<AlunoModel> getByNome(String nome){
        List<AlunoModel> alunos = getAll();
        List<AlunoModel> alunosNomesIguais = new ArrayList<>();
        alunos.forEach(aluno -> {
            if (aluno.getNome().toLowerCase().equals(nome.toLowerCase())){
                alunosNomesIguais.add(aluno);
            }
        });
        if (alunosNomesIguais.isEmpty()){
            throw new NomeErrorException("Nenhum Aluno com esse Nome {" + nome + "} encontrado." );
        } else {
            return alunosNomesIguais;
        }
    }

    public void deleteById(int id) {
        alunos.remove(ValidaId(id).getId());
    }

    public void updateById(int id, AlunoModel alunoModel) {
        alunoModel.setId(id);
        alunos.replace(id, alunoModel);
    }

    public void post(AlunoModel alunoModel) {
        int id = ++this.lastId;
        alunoModel.setId(id);
        alunoModel.setBairro(apiService.getApi(alunoModel.getCep()).getNeighborhood());
        alunoModel.setUF(apiService.getApi(alunoModel.getCep()).getState());
        alunos.put(alunoModel.getId(), alunoModel);
    }

    private AlunoModel ValidaId(int id) {
        if (alunos.containsKey(id)){
            return alunos.get(id);
        } else {
            throw new IdErrorException("Aluno com Id {" + id + "} nao encontrado.");
        }
    }
}
