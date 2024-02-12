package com.example;
 // holiii aca la main no tiene gran cosa, por ahora no tengo
 // ingresos por consola, asi q solo meto manuealmente el tamano de la matriz y
 // el numero de turnos
public class Main {
    public static void main(String[] args) {
        int filas = 8;
        int columnas = 8;
        int totalTurnos = 3;

        Tablero tablero = new Tablero(filas, columnas, totalTurnos);
        tablero.iniciarSimulacion();
    }
}

