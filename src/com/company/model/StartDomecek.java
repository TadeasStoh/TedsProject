package com.company.model;

import java.util.*;

public class StartDomecek {

    private final Figs[] figurkyDoma;

    public Figs[] getFigurkyDoma() {
        return figurkyDoma;
    }

    public StartDomecek(BarFig abstro, int pocetFig) {
        barva = abstro;

        figurkyDoma = new Figs[pocetFig];
        for (int i = 0; i < pocetFig; i++) {
            figurkyDoma[i] = new Figs(barva);
        }
    }

    public Figs nasaditFig(int fig) {
        Figs nasazovanaFig = figurkyDoma[fig];
        figurkyDoma[fig] = null;
        return nasazovanaFig;
    }

    public void vratitFig(Figs vracenaFig) {
        for(int i = 0; i < 4; i++) {
            if(figurkyDoma[i] == null) {
                figurkyDoma[i] = vracenaFig;
            }
        }
    }

    private BarFig barva;
}