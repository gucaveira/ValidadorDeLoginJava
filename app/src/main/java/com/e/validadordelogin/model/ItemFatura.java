package com.e.validadordelogin.model;

import com.e.validadordelogin.util.MoedaUtil;
import com.google.gson.annotations.SerializedName;

public class ItemFatura {

    @SerializedName("title")
    private String titulo;

    @SerializedName("desc")
    private String desc;

    @SerializedName("date")
    private String data;

    @SerializedName("value")
    private Double valor;

    public String getTitulo() {
        return titulo;
    }

    public String getDesc() {
        return desc;
    }

    public String getData() {
        return data;
    }


    public Double getValor() {
        return valor;
    }

    public String mostraPreco(Double valor) {
        return MoedaUtil.formataBrasileiro(valor);
    }
}
