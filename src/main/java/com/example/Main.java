package com.example;

public class Main {
    public static void main(String[] args) {
        int filas = 8;
        int columnas = 8;
        int totalTurnos = 3;

        Tablero tablero = new Tablero(filas, columnas, totalTurnos);
        tablero.iniciarSimulacion();
    }
}

