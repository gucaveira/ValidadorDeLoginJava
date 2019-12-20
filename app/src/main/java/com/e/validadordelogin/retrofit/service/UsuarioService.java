package com.e.validadordelogin.retrofit.service;

import com.e.validadordelogin.model.Extrato;
import com.e.validadordelogin.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsuarioService {

    @POST("login")
    Call<Usuario> buscaUsuarioApi();

    @GET("list/{id}")
    Call<List<Extrato>> buscaExtradoApi(@Path("id")String id);
}