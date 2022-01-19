package com.company.model;

import javax.swing.*;
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

    public boolean isByloHozeno() {
        return byloHozeno;
    }

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

        kolikratHozeno = 0;

        praveHraje = hraci.get(0);
    }

    public boolean jeMoje(int kde) {
        if(plocha[kde] == null) {
            return false;
        }
        else {
            return plocha[kde].getBarva() == praveHraje;
        }
    }

    private int kolikratHozeno;

    public void hoditKostkou() {
        if(!byloHozeno) {
            hod = kostka.hod();
            if(domecky.get(praveHraje).mamVsechnyFigurky()) {
                if(kolikratHozeno < 3) {
                    kolikratHozeno++;
                    if(hod == kostka.getPocetSten()) {
                        byloHozeno = true;
                    }
                    else if(kolikratHozeno == 3) {
                        konecTahu();
                    }
                }
            }
            else {
                byloHozeno = true;
            }
        }
    }

    public int kolikHozeno() {
        return hod;
    }

    public void nasaditFigurku(int fig) {
        if(!byloHozeno) return;

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

                byloHozeno = false;
            }
        }
    }

    public void posunFigurky(int kde) {
        if(!byloHozeno) return;

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
        else if(plocha[spocitanaCesta(kam)].getBarva() == praveHraje) {
            return;
        }

        plocha[kde] = null;

        konecTahu();
    }

    public void posunFigurkyVCili(int kde) {
        if(!byloHozeno) return;

        Figs jaka = cile.get(praveHraje).getCil()[kde];
        int kam = kde + hod;

        if(jaka != null) {
            if(kam < 4) {
                if(cile.get(praveHraje).jeVolno(kam)) {
                    cile.get(praveHraje).posunVCili(jaka, kde, kam);
                }
            }
        }

        konecTahu();
    }

    public void vyhodit(int kde) {
        BarFig koho = plocha[kde].getBarva();
        domecky.get(koho).vratitFig(plocha[kde]);
        plocha[kde] = null;
    }

    public void konecTahu() {
        if(cile.get(praveHraje).mamFullHouse()) {
            konecHry();
        }

        if(hod != kostka.getPocetSten()) {
            if(praveHraje.getPoradi() < hraci.size() - 1) {
                praveHraje = hraci.get(praveHraje.getPoradi() + 1);
            }
            else {
                praveHraje = hraci.get(0);
            }
        }
        byloHozeno = false;
        kolikratHozeno = 0;
    }

    private void konecHry() {
        JOptionPane.showMessageDialog(null, "Vyhral hrac " + (praveHraje.getPoradi() + 1));
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