package com.e.validadordelogin.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Usuario implements Serializable {

    @SerializedName("name")
    private String nome;

    @SerializedName("age")
    private String idade;

    @SerializedName("email")
    private String email;

    public Usuario(String cpf, String senha) {
    }

    public String getNome() {
        return nome;
    }

    public String getIdade() {
        return idade;
    }

    public String getEmail() {
        return email;
    }
}