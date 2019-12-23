package com.e.validadordelogin.ui.validador;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerificadorDeTexto {

    public static boolean verificaSeEhEmailOuCpf(final String senhaOuEmail) {
        Pattern patterns;
        Matcher matcher;

        final String test = "^\\w+([.-]?\\w+)@\\w+([.-]?\\w+)(.\\w{1,3})+$";

        patterns = Pattern.compile(test);
        matcher = patterns.matcher(senhaOuEmail);

        return matcher.matches();
    }
}
