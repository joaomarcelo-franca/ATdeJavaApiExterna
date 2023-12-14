package br.com.infnet.java.model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ResponsePayload {
    private String mensagem;
    private LocalDateTime HoraAtual;
    public ResponsePayload(String mensagem){
        this.mensagem = mensagem;
        this.HoraAtual = LocalDateTime.now();
    }

}
