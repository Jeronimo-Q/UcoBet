package com.jeronimo.ucobet.dominio;

public class Apuesta {
    private String numeroApostado;
    private long monto;

    public Apuesta(String numeroApostado, long monto) {
        this.numeroApostado = numeroApostado;
        this.monto = monto;
    }

    public String getNumeroApostado() {
        return numeroApostado;
    }

    public long getMonto() {
        return monto;
    }

    @Override
    public String toString() {
        return "Apuesta{" +
                "numeroApostado='" + numeroApostado + '\'' +
                ", monto=" + monto +
                '}';
    }
}
