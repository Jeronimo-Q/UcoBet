package com.jeronimo.ucobet.app;
import com.jeronimo.ucobet.dominio.UcoBet;

public class AppUcoBet {

    public static void main(String[] args) {

        UcoBet apuestas = new UcoBet();

        //Ingreso de datos desde la consola del usuario
        for (int i = 0; i < 2; i++) {
           apuestas.ingreso();
           apuestas.historial();
        }
    }
}
