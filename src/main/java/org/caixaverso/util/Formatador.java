package org.caixaverso.util;

import jakarta.enterprise.context.ApplicationScoped;

import java.text.NumberFormat;
import java.util.Locale;

@ApplicationScoped
public class Formatador {

    private static final Locale localeBR = new Locale("pt", "BR");

    public static String formatarReais(double valor) {
        return NumberFormat.getCurrencyInstance(localeBR).format(valor);
    }

    public static String formatarPercentual(double taxa) {
        return String.format("%.2f%%", taxa * 100);
    }
}
