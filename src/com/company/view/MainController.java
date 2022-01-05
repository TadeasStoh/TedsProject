package com.company.view;
import com.company.model.HracPloch;
import com.company.model.Kostka;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.fxml.FXML;

import java.awt.*;
import java.util.Random;

public class MainController {

    HracPloch hraciplocha;

    int[][] mara = new int[][] {
            {0 , 0 , 0 , 0 , 9 , 10, 11, 0 , 0 , 0 , 0 },
            {0 , 0 , 0 , 0 , 8 , 0 , 12, 0 , 0 , 0 , 0 },
            {0 , 0 , 0 , 0 , 7 , 0 , 13, 0 , 0 , 0 , 0 },
            {0 , 0 , 0 , 0 , 6 , 0 , 14, 0 , 0 , 0 , 0 },
            {1 , 2 , 3 , 4 , 5 , 0 , 15, 16, 17, 18, 19},
            {40, 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 20},
            {39, 38, 37, 36, 35, 0 , 25, 24, 23, 22, 21},
            {0 , 0 , 0 , 0 , 34, 0 , 26, 0 , 0 , 0 , 0 },
            {0 , 0 , 0 , 0 , 33, 0 , 27, 0 , 0 , 0 , 0 },
            {0 , 0 , 0 , 0 , 32, 0 , 28, 0 , 0 , 0 , 0 },
            {0 , 0 , 0 , 0 , 31, 30, 29, 0 , 0 , 0 , 0 },
            };

    int[][][] domecky = new int[][][] {
            {{0, 0}, {1, 0}, {1, 1}, {0, 1}},
            {{9, 0}, {10, 0}, {10, 1}, {9, 1}},
            {{9, 9}, {10, 9}, {10, 10}, {9, 10}},
            {{0, 9}, {1, 9}, {1, 10}, {0, 10}}
    };

    int[][][] cile = new int[][][] {
            {{1, 5}, {2, 5}, {3, 5}, {4, 5}},
            {{5, 1}, {5, 2}, {5, 3}, {5, 4}},
            {{9, 5}, {8, 5}, {7, 5}, {6, 5}},
            {{5, 9}, {5, 8}, {5, 7}, {5, 6}}
    };

    private String[] barvy = new String[] {
            "ff0000",
            "00aaff",
            "00cc00",
            "eecc00",
            };

    @FXML
    public FlowPane Standa;

    @FXML
    public GridPane Lada;

    @FXML
    public Button[] tlacenky;

    @FXML
    public Button[][] ciklonky;

    @FXML
    public void novaplocha(){

        hraciplocha = new HracPloch(40, 4, 4, new Kostka(6));

        tlacenky = new Button[40];

        for (int i=0; i<11; i++){
            for(int i1=0; i1<11; i1++){
                if(mara[i1][i] > 0){
                    tlacenky[mara[i1][i] - 1] = new Button(String.valueOf(mara[i1][i] - 1));
                    tlacenky[mara[i1][i] - 1].setPrefSize(Lada.getWidth() / 11, Lada.getHeight() / 11);
                    tlacenky[mara[i1][i] - 1].setFocusTraversable(false);
                    int ii = i;
                    int ii1 = i1;
                    tlacenky[mara[i1][i] - 1].setOnAction(a ->
                    {
                        klikPole(mara[ii1][ii] - 1);
                    });

                    Lada.add(tlacenky[mara[i1][i] - 1],i,i1);
                }
            }
        }

        ciklonky = new Button[4][4];

        for(int h = 0; h < 4; h++) {
            for(int c = 0; c < 4; c++) {
                ciklonky[h][c] = new Button(String.valueOf(c + 1));
                ciklonky[h][c].setPrefSize(Lada.getWidth() / 11, Lada.getHeight() / 11);
                ciklonky[h][c].setFocusTraversable(false);
                ciklonky[h][c].setDefaultButton(true);

                Lada.add(ciklonky[h][c], cile[h][c][0], cile[h][c][1]);
            }
        }

        for(int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                Button pepik = new Button(String.valueOf(b + 1));
                pepik.setPrefSize(Lada.getWidth() / 11, Lada.getHeight() / 11);
                pepik.setFocusTraversable(false);
                pepik.setStyle("-fx-border-color: #" + barvy[a] + ";" +
                        "-fx-border-width: 2" + ";" +
                        "-fx-border-insets: 0" + ";");

                Lada.add(pepik, domecky[a][b][0], domecky[a][b][1]);
            }
        }


        Button kostka = new Button("X");
        kostka.setPrefSize(Lada.getWidth() / 11, Lada.getHeight() / 11);
        kostka.setFocusTraversable(false);
        kostka.setOnAction(actionEvent ->
        {
            hraciplocha.hoditKostkou();
            kostka.setText(String.valueOf(hraciplocha.kolikHozeno()));
        });

        Lada.add(kostka, 5, 5);

        aktualizacePlochy();
    }

    public void klikPole(int pole){
        if(!hraciplocha.jeMoje(pole) || hraciplocha.kolikHozeno() == 0) return;

        hraciplocha.posunFigurky(pole);

        aktualizacePlochy();
    }

    public void aktualizacePlochy() {
        for(int i = 0; i < tlacenky.length; i++) {
            if(hraciplocha.getPlocha()[i] == null) {
                tlacenky[i].setStyle("");
            }
            else {
                tlacenky[i].setStyle("-fx-background-color: #" + barvy[hraciplocha.getPlocha()[i].getBarva().getPoradi()]);
            }
        }
        for(int h = 0; h < ciklonky.length; h++) {
            for(int c = 0; c < ciklonky[h].length; c++) {
                if(hraciplocha.getCile().get(hraciplocha.getHraci().get(h)).getCil()[c] == null) {
                    ciklonky[h][c].setStyle("");
                }
                else {
                    ciklonky[h][c].setStyle("-fx-background-color: #" + barvy[hraciplocha.getCile().get(hraciplocha.getHraci().get(h)).getCil()[c].getBarva().getPoradi()]);
                }
            }
        }
    }

    /*

    @FXML
    public void vytvoreniPlochy(){
        Button[] policka=new Button[40];
        for (int i=0; i<40; i++){
            policka [i]= new Button();
            policka[i].setOnAction(e -> System.out.println("Hello"));
        }
        Standa.getChildren().addAll(policka);
    }

    public void vytovreniStartDomecku(){
        Button[] policka=new Button[16];
        for (int i=0; i<16; i++) {
            policka[i] = new Button();
        }
        Standa.getChildren().addAll(policka);
    }

    public void vytvoreniFinalDomecku(){
        Button[] policka=new Button[16];
        for (int i=0; i<16; i++) {
            policka[i] = new Button();
        }
        Standa.getChildren().addAll(policka);
    }


     */
}