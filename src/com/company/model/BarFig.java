package com.company.model;

public class BarFig {
    private final int poradi;
    public int getPoradi() {return poradi;}
    private final int startovniPole;
    private final int vstupDoCile;

    private boolean muzuHrat;

    public BarFig(int p, int s, int v) {
        poradi = p;
        startovniPole = s;
        vstupDoCile = v;
    }

    public int getStartovniPole(){return startovniPole;}
    public int getVstupDoCile(){return vstupDoCile;}
}