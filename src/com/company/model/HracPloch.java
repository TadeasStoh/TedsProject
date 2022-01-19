package com.company.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class HracPloch {

    private final Figs[] plocha;
    public Figs[] getPlocha(){
        return plocha;
    }

    private final int delkaPlochy;

    private final List<BarFig> hraci = new ArrayList<>();
    private final int pocetHracu;

    public List<BarFig> getHraci() {
        return hraci;
    }

    private final Map<BarFig, StartDomecek> domecky = new HashMap<>();
    private final Map<BarFig, CilDomecek> cile = new HashMap<>();

    public Map<BarFig, StartDomecek> getDomecky() {
        return domecky;
    }
    public Map<BarFig, CilDomecek> getCile() {
        return cile;
    }

    private final Kostka kostka;
    private boolean byloHozeno;
    private int hod;

    private BarFig praveHraje;

    public BarFig getPraveHraje() {
        return praveHraje;
    }

    public HracPloch(int vel, int pocFig, int pocHracu, Kostka k) {
        delkaPlochy = vel;
        pocetHracu = pocHracu;
        kostka = k;

        plocha = new Figs[delkaPlochy];

        for(int i = 0; i < pocetHracu; i++) {
            BarFig novyHrac = new BarFig(i, 10 * i, spocitanaCesta(10 * i - 1));

            hraci.add(novyHrac);

            domecky.put(novyHrac, new StartDomecek(novyHrac, pocFig));
            cile.put(novyHrac, new CilDomecek(novyHrac, pocFig));
        }

        praveHraje = hraci.get(3);

        Figs figs = new Figs(hraci.get(3));
        plocha[4] = figs;

        Figs figss = domecky.get(hraci.get(1)).nasaditFig(1);
        plocha[7] = figss;
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

    public void nasaditFigurku(int fig) {
        if(hod == kostka.getPocetSten()) {
            if(domecky.get(praveHraje).getFigurkyDoma()[fig] != null) {
                if(plocha[praveHraje.getStartovniPole()] == null) {
                    plocha[praveHraje.getStartovniPole()] = domecky.get(praveHraje).nasaditFig(fig);
                }
                else if(plocha[praveHraje.getStartovniPole()].getBarva() != praveHraje)
                {
                    vyhodit(praveHraje.getStartovniPole());
                    plocha[praveHraje.getStartovniPole()] = domecky.get(praveHraje).nasaditFig(fig);
                }
            }
        }
    }

    public void posunFigurky(int kde) {
        Figs jaka = plocha[kde];
        int kam = kde + hod;

        if(kam > praveHraje.getVstupDoCile() && kde <= praveHraje.getVstupDoCile()) {
            int kamDoCile = kam - praveHraje.getVstupDoCile() - 1;
            if(cile.get(praveHraje).jeVolno(kamDoCile)) {
                cile.get(praveHraje).jitDoCile(jaka, kamDoCile);
            }
        }
        else if(plocha[spocitanaCesta(kam)] == null) {
            plocha[spocitanaCesta(kam)] = jaka;
        }
        else if(plocha[spocitanaCesta(kam)].getBarva() != praveHraje) {
            vyhodit(spocitanaCesta(kam));
            plocha[spocitanaCesta(kam)] = jaka;
        }

        plocha[kde] = null;
    }

    public void posunFigurkyVCili(int kde) {
        Figs jaka = cile.get(praveHraje).getCil()[kde];
        int kam = kde + hod;

        if(jaka != null) {
            if(kam < 4) {
                if(cile.get(praveHraje).jeVolno(kam)) {
                    cile.get(praveHraje).posunVCili(jaka, kde, kam);
                }
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
        while(1<2) {
            for (int i = 0; i < 1000000; i++) {
                System.out.println(i);
            }
        }
    }

    public int spocitanaCesta(int kam) {
        int cesta = kam;

        if(kam > delkaPlochy - 1) {
            cesta -= delkaPlochy;
        }
        else if (kam < 0) {
            cesta = delkaPlochy + kam;
        }

        return cesta;
    }


}