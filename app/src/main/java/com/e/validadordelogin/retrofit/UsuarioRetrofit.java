package com.e.validadordelogin.retrofit;

import com.e.validadordelogin.retrofit.service.UsuarioService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsuarioRetrofit {

    public static final String BASE_URL = "https://diego.free.beeceptor.com/";
    private final UsuarioService usuarioService;

    public UsuarioRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        usuarioService = retrofit.create(UsuarioService.class);
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }
}
