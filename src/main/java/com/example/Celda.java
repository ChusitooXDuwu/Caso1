package com.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Celda extends Thread {
    private volatile int estadoActual;
    private final List<Celda> vecinos = new ArrayList<>();
    private final Tablero tablero;
    private final Queue<Integer> bufferEstados; // Buffer para almacenar estados recibidos de vecinos
    private final int capacidadBuffer; // Capacidad máxima del buffer, basada en la fila

    public Celda(Tablero tablero, int fila) {
        this.tablero = tablero;
        this.estadoActual = Math.random() < 0.5 ? 1 : 0;
        this.capacidadBuffer = fila + 1;
        this.bufferEstados = new LinkedList<>();
    }

    public synchronized void agregarVecino(Celda vecino) {
        vecinos.add(vecino);
    }

    public int getEstadoActual() {
        return estadoActual;
    }

    public synchronized void recibirEstadoDeVecino(int estado) {
        while (bufferEstados.size() >= capacidadBuffer) {
            try {
                wait(); // Espera si el buffer está lleno
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        bufferEstados.add(estado);
        notifyAll(); // Notifica que se ha agregado un estado al buffer
    }

    private synchronized int leerEstadoDelBuffer() {
        while (bufferEstados.isEmpty()) {
            try {
                wait(); // Espera si el buffer está vacío
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        int estado = bufferEstados.poll();
        notifyAll(); // Notifica que se ha leído un estado del buffer
        return estado;
    }

    private void notificarVecinos() {
        vecinos.forEach(vecino -> vecino.recibirEstadoDeVecino(this.estadoActual));
    }

    private void calcularProximoEstado() {
        int vivos = 0;
    
        // Asumiendo que `leerEstadoDelBuffer` devuelve correctamente los estados de los vecinos.
        synchronized (this) {
            while (!bufferEstados.isEmpty()) {
                int estadoVecino = leerEstadoDelBuffer(); // Este método debería hacer poll() del buffer
                if(estadoVecino == 1) {
                    vivos++; // Solo incrementa si el vecino está vivo
                }
            }
        }
    
        // Decide el nuevo estado basado en las reglas del Juego de la Vida
        int nuevoEstado = (estadoActual == 1 && (vivos == 2 || vivos == 3)) || (estadoActual == 0 && vivos == 3) ? 1 : 0;
        this.estadoActual = nuevoEstado;
        
    }
    

    
    @Override
public void run() {
    try {
        while (!Thread.interrupted()) {
            Thread.sleep(100);
            notificarVecinos();
            calcularProximoEstado();
            tablero.notificarCeldaListo(); // Ajustado para no pasar argumentos
        }
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

}
