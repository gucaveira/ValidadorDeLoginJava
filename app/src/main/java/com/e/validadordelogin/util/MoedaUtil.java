package com.e.validadordelogin.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MoedaUtil {

        public static String formataBrasileiro(Double valor) {
        NumberFormat formataBrasileiro = DecimalFormat.getCurrencyInstance(
                new Locale("pt", "br"));
        return formataBrasileiro.format(valor).replace("-R$", "R$ -");

    }
}

