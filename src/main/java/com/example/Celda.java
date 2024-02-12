package com.example;

import java.util.ArrayList;
import java.util.List;

public class Celda extends Thread {
    private volatile int estadoActual; 
    private final List<Celda> vecinos = new ArrayList<>();
    private final Tablero tablero; 
    
    public Celda(Tablero tablero) {
        this.tablero = tablero;
        this.estadoActual = Math.random() < 0.5 ? 1 : 0; .
    }

    public void agregarVecino(Celda vecino) {
        synchronized (vecinos) {
            vecinos.add(vecino);
        }
    }

    public int getEstadoActual() {
        return estadoActual;
    }

    private void calcularProximoEstado() {
        int vivos = (int) vecinos.stream().filter(v -> v.getEstadoActual() == 1).count(); // Cuenta la cantidad de vecinos vivos, es una line algo rara de codigo
        estadoActual = (estadoActual == 1 && (vivos == 2 || vivos == 3)) || (estadoActual == 0 && vivos == 3) ? 1 : 0;
    }

    @Override
    public void run() {
        try {
            
            while (!Thread.interrupted()) {
                Thread.sleep(100); 
                calcularProximoEstado();
                
                // Aca falta hacer la sincronización con el controlador de turnos
                tablero.notificarCeldaListo();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
