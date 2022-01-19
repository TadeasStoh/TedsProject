package com.company.model;

import java.util.Map;
import java.util.HashMap;

public class CilDomecek {
    private BarFig barva;

    private Figs[] cil;
    private int pocetFigurek;

    public CilDomecek(BarFig b, int pocFigurek) {
        barva = b;
        pocetFigurek = pocFigurek;

        cil = new Figs[pocFigurek];
    }

    public boolean jeVolno(int kde) {
        return cil[kde] == null;
    }

    public void jitDoCile(Figs kdo, int kam) {
        cil[kam] = kdo;
    }

    public void posunVCili(Figs kdo, int odkud, int kam) {
        cil[kam] = kdo;
        cil[odkud] = null;
    }

    public Figs[] getCil() {
        return cil;
    }

    public boolean mamTuhleFigurku(Figs f) {
        boolean ano = false;

        for(int i = 0; i < cil.length; i++) {
            if(cil[i] == f) {
                ano = true;
                break;
            }
        }

        return ano;
    }

    public boolean mamFullHouse() {
        int figs = 0;

        for(int i = 0; i < cil.length; i++) {
            if(cil[i] != null) {
                figs++;
            }
        }

        return figs == cil.length;
    }
}
