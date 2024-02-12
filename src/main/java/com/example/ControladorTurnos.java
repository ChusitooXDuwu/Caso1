package com.example;


public class ControladorTurnos {
    private final int totalCeldas;
    private int celdasListas = 0;
    private final Object lock = new Object();
    private int turnosCompletados = 0;
    private final int totalTurnos;

    public ControladorTurnos(int totalCeldas, int totalTurnos) {
        this.totalCeldas = totalCeldas;
        this.totalTurnos = totalTurnos;
    }

    public void registrarCeldaListo() {
        synchronized (lock) {
            celdasListas++;
            if (celdasListas == totalCeldas) {
                celdasListas = 0; 
                turnosCompletados++;
                lock.notifyAll(); 
            }
        }
    }
    public void esperarTurnoCompleto() throws InterruptedException {
        synchronized (lock) {
            while (celdasListas < totalCeldas) {
                lock.wait();
            }
        }
    }

    public synchronized boolean turnosCompletados() {
        return turnosCompletados >= totalTurnos;
    }
}
