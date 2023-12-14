package br.com.infnet.java.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoModel {
    private int Id;
    private String Cep;
    private String UF;
    private String bairro;
    private String nome;
    private String[] cursosDesejados = new String[3];

}
