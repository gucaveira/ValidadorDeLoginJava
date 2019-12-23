package com.e.validadordelogin.ui.validador;

import java.util.InputMismatchException;

public class ValidadorCpf{

    private static int sm;
    private static int peso;
    private static int r;
    private static int num;
    private static char dig10;
    private static char dig11;

    private static final int POSICAO_0_TABELA_ASCII = 48;

    public static boolean isCPF(String CPF) {
        if (cpfsInvalidos(CPF))
            return (false);

        try {
            calculandoPrimeiraParte(CPF);
            calculandoPrimeirasegunda(CPF);
            return (dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10));
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    private static void calculandoPrimeiraParte(String CPF) {
        sm = 0;
        peso = 10;
        for (int i = 0; i < 9; i++) {
            num = (int) (CPF.charAt(i) - POSICAO_0_TABELA_ASCII);
            sm += (num * peso);
            peso = peso - 1;
        }

        r = 11 - (sm % 11);
        if ((r == 10) || (r == 11)) {
            dig10 = '0';
        } else {
            dig10 = (char) (r + 48);
        }
    }

    private static void calculandoPrimeirasegunda(String CPF) {
        sm = 0;
        peso = 11;
        for (int i = 0; i < 10; i++) {
            num = (int) (CPF.charAt(i) - POSICAO_0_TABELA_ASCII);
            sm = sm + (num * peso);
            peso = peso - 1;
        }
        r = 11 - (sm % 11);
        if ((r == 10) || (r == 11)) {
            dig11 = '0';
        } else {
            dig11 = (char) (r + 48);
        }
    }

    private static boolean cpfsInvalidos(String CPF) {
        return CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11);
    }

    public static String imprimeCpf(String CPF) {
        return (CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." + CPF.substring(6, 9) + "-"
                + CPF.substring(9, 11));
    }
}
