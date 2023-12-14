package br.com.infnet.java.model;

import lombok.Data;

@Data
public class ApiModel {
    public String cep;
    public String state;
    public String city;
    public String neighborhood;
    public String street;
    public String service;
}
