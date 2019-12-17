package com.e.validadordelogin.retrofit.service;

import com.e.validadordelogin.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsuarioService {

    @GET("list/{id}")
    Call<List<Usuario>> buscaUsuarioApi(@Path("id") String userid);
}
