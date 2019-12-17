package com.e.validadordelogin.retrofit.service;

import com.e.validadordelogin.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UsuarioService {

    @GET
    Call<List<Usuario>> buscarUsuario();
}
