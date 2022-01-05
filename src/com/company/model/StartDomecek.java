package com.company.model;

import java.util.*;


public class StartDomecek {

    public List<Figs> figurkyDom = new ArrayList<>();

    public StartDomecek(BarFig abstro, int PocetFig) {
        barva = abstro;
        for (int i = 0; i < PocetFig; i++) {
            figurkyDom.add(new Figs(barva));
        }
    }
    public Figs nasaditFig () {
        Figs nasazovanaFig = figurkyDom.get(figurkyDom.size() - 1);
        figurkyDom.remove(figurkyDom.size() - 1);
        return nasazovanaFig;

    }

    public void vratitFig(Figs vracenaFig) {
        figurkyDom.add(vracenaFig);

    }
    private BarFig barva;
}