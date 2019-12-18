package com.e.validadordelogin.retrofit.service;

import com.e.validadordelogin.model.Usuario;

import retrofit2.Call;
import retrofit2.http.POST;

public interface UsuarioService {

    @POST("login")
    Call<Usuario> buscaUsuarioApi();
}