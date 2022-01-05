package com.company.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class HracPloch {

    private Figs[] plocha;
    public Figs[] getPlocha(){
        return plocha;
    }

    private int delkaPlochy;

    private List<BarFig> hraci = new ArrayList<>();
    private int pocetHracu;

    public List<BarFig> getHraci() {
        return hraci;
    }

    private Map<BarFig, StartDomecek> domecky = new HashMap<>();
    private Map<BarFig, CilDomecek> cile = new HashMap<>();

    public Map<BarFig, CilDomecek> getCile() {
        return cile;
    }

    private int pocetFigurek;
    private Kostka kostka;
    private boolean byloHozeno;
    private int hod;

    public BarFig praveHraje;

    public HracPloch(int vel, int pocFig, int pocHracu, Kostka k) {
        delkaPlochy = vel;
        pocetFigurek = pocFig;
        pocetHracu = pocHracu;
        kostka = k;

        plocha = new Figs[delkaPlochy];

        for(int i = 0; i < pocetHracu; i++) {
            BarFig novyHrac = new BarFig(i, 10 * i, spocitanaCesta(10 * i - 1));

            hraci.add(novyHrac);

            domecky.put(novyHrac, new StartDomecek(novyHrac, pocetFigurek));
            cile.put(novyHrac, new CilDomecek(novyHrac, pocetFigurek));
        }

        praveHraje = hraci.get(3);

        Figs figs = new Figs(hraci.get(3));
        plocha[4] = figs;
    }

    public boolean jeVolno(int kde) {
        return plocha[kde] == null;
    }

    public boolean jeMoje(int kde) {
        if(plocha[kde] == null) {
            return false;
        }
        else {
            return plocha[kde].getBarva() == praveHraje;
        }
    }

    public void hoditKostkou() {
        if(!byloHozeno) {
            byloHozeno = true;
            hod = kostka.hod();
        }
    }

    public int kolikHozeno() {
        return (byloHozeno) ? hod : 0;
    }

    public void nasaditFigurku() {
        if(jeVolno(praveHraje.getStartovniPole())) {
            plocha[praveHraje.getStartovniPole()] = domecky.get(praveHraje).nasaditFig();
        }
        else if(plocha[praveHraje.getStartovniPole()].getBarva() != praveHraje) {
            vyhodit(praveHraje.getStartovniPole());
            plocha[praveHraje.getStartovniPole()] = domecky.get(praveHraje).nasaditFig();
        }
    }

    public void posunFigurky(int kde) {
        Figs jaka = plocha[kde];
        int kam = kde + hod;

        if(!cile.get(praveHraje).mamTuhleFigurku(jaka)) {
            if(kam > praveHraje.getVstupDoCile() && kde < praveHraje.getVstupDoCile()) {
                int kamDoCile = kam - praveHraje.getVstupDoCile() - 1;
                if(cile.get(praveHraje).jeVolno(kamDoCile)) {
                    cile.get(praveHraje).jitDoCile(jaka, kamDoCile);
                    plocha[kde] = null;
                }
            }
            else if(jeVolno(spocitanaCesta(kam))) {
                plocha[spocitanaCesta(kam)] = jaka;
                plocha[kde] = null;
            }
            else if(plocha[spocitanaCesta(kam)].getBarva() != praveHraje) {
                vyhodit(spocitanaCesta(kam));
                plocha[spocitanaCesta(kam)] = jaka;
            }
        }
        else {
            if(cile.get(praveHraje).jeVolno(kam)) {
                cile.get(praveHraje).posunVCili(jaka, kde, kam);
            }
        }
    }

    public void vyhodit(int kde) {
        BarFig koho = plocha[kde].getBarva();
        domecky.get(koho).vratitFig(plocha[kde]);
        plocha[kde] = null;
    }

    public void konecTahu() {
        byloHozeno = false;
        praveHraje = hraci.get(praveHraje.getPoradi() + 1);
    }

    public void konecHry() {
        // KONEC
    }

    public int spocitanaCesta(int kam) {
        return (kam > delkaPlochy - 1) ? kam - delkaPlochy : kam;

    }


}