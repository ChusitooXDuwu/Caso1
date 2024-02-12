package com.example;

public class Tablero {
    private final Celda[][] celdas;
    private final int filas;
    private final int columnas;
    private final int totalTurnos;
    private int celdasListasParaSiguienteTurno = 0; 


    public Tablero(int filas, int columnas, int totalTurnos) {
        this.filas = filas;
        this.columnas = columnas;
        this.totalTurnos = totalTurnos;
        this.celdas = new Celda[filas][columnas];
        inicializarCeldas();// inicializa ccada celda del tablero
    }

    private void inicializarCeldas() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Celda(this,i); // aca recibe x parametro el tablero y la fila
            }
        }
    }

    
    
    public synchronized void notificarCeldaListo() {
        celdasListasParaSiguienteTurno++;
        if (celdasListasParaSiguienteTurno == filas * columnas) {
            
            celdasListasParaSiguienteTurno = 0;
            // aca espera para que todas las celdas esten listas para el siguiente turno
            notifyAll(); 
        }
    }

    public synchronized void iniciarSimulacion() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j].start();
            }
        }

        for (int turno = 0; turno < totalTurnos; turno++) {
            try {
                
                Thread.sleep(100); 
                imprimirTablero();
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j].interrupt();
            }
        }
    }

    public void imprimirTablero() {
    	
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(celdas[i][j].getEstadoActual() == 1 ? "X" : ".");
                
            }
            
            System.out.println();
        }
        System.out.print("---------------"); // aca intento separar las iteraciones de los threads
        
    }
}
