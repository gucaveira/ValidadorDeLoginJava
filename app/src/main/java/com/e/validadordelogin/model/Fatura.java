package com.e.validadordelogin.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Fatura {

    @SerializedName("statementList")
    public List<ItemFatura>lista;

    public class ItemFatura {
        @SerializedName("title")
        public String titulo;

        @SerializedName("desc")
        public String desc;

        @SerializedName("date")
        public String data;

        @SerializedName("value")
        public Double valor;

    public ItemFatura(String desc, String date, Double valor) {
            this.desc = desc;
            this.data = date;
            this.valor = valor;
        }

    public ItemFatura() {
        }

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
    }



}
