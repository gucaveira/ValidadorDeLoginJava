package com.e.validadordelogin.model;

import com.google.gson.annotations.SerializedName;

public class Extrato {

    private String title;
    private String desc;
    private String date;
    private Double value;

    public Extrato(String desc, String date, Double value) {
        this.desc = desc;
        this.date = date;
        this.value = value;
    }

    public Extrato() {
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getDate() {
        return date;
    }

    public Double getValue() {
        return value;
    }
}
