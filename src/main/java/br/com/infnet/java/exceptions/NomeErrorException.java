package br.com.infnet.java.exceptions;

public class NomeErrorException extends RuntimeException{
    public NomeErrorException(String mensagem){
        super(mensagem);
    }
}
