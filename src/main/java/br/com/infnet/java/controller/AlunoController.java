package br.com.infnet.java.controller;

import br.com.infnet.java.exceptions.IdErrorException;
import br.com.infnet.java.exceptions.NomeErrorException;
import br.com.infnet.java.model.AlunoModel;
import br.com.infnet.java.model.ResponsePayload;
import br.com.infnet.java.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/aluno")
@RestController
public class AlunoController {
    @Autowired
    AlunoService alunoService;

    @GetMapping
    public ResponseEntity GetAll(@RequestParam(required = false) String nome){
        try{
            if (nome == null){
                return ResponseEntity.ok().body(alunoService.getAll());

            } else {
                return ResponseEntity.ok().body(alunoService.getByNome(nome));
            }
        } catch (NomeErrorException ex){
            ResponsePayload responsePayload = new ResponsePayload(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsePayload);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity GetById(@PathVariable int id){
        try{
            return ResponseEntity.ok().body(alunoService.getById(id));
        } catch (IdErrorException ex){
            ResponsePayload responsePayload = new ResponsePayload(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsePayload);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity DeleteById(@PathVariable int id){
        try{
            alunoService.deleteById(id);
            return ResponseEntity.ok().body(new ResponsePayload("Aluno de Id {" + id + "} Deletado com sucesso!"));
        } catch (IdErrorException ex){
            ResponsePayload responsePayload = new ResponsePayload(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsePayload);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity UpdateById(@PathVariable int id, @RequestBody AlunoModel alunoModel){
        try {
            alunoService.updateById(id, alunoModel);
            return ResponseEntity.ok().body("Aluno de Id {" + id + "} atualizado com sucesso!");
        } catch (IdErrorException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/criar")
    public ResponseEntity Post(@RequestBody AlunoModel alunoModel){
        try {
            alunoService.post(alunoModel);
            return ResponseEntity.status(HttpStatus.CREATED).body("Aluno criado com sucesso!");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar aluno");
        }
    }
}
