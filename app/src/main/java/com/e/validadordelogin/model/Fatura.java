package com.e.validadordelogin.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Fatura {

    @SerializedName("statementList")
    private List<ItemFatura> itemFaturas;

    public List<ItemFatura> getItemFaturas() {
        return itemFaturas;
    }
}
