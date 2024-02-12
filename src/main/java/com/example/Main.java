package com.example;

public class Main {
    public static void main(String[] args) {
        int filas = 4;
        int columnas = 4;
        int totalTurnos = 3;

        Tablero tablero = new Tablero(filas, columnas, totalTurnos);
        tablero.iniciarSimulacion();
    }
}

