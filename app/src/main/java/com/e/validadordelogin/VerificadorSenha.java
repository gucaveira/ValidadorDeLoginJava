package com.e.validadordelogin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerificadorSenha {

    public static boolean verificaSenha(final String senha) {
        Pattern pattern;
        Matcher matcher;

        final String SENHA_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()_;.,?:])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(SENHA_PATTERN);
        matcher = pattern.matcher(senha.trim());

        return matcher.matches();
    }


}
