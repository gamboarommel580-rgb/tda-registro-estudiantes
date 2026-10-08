package Implementar_Pila;

import java.util.ArrayDeque;
import java.util.Queue;

public class Ejercicio2Turnos {

    public static void main(String[] args) {
        Queue<String> turnos = new ArrayDeque<>();

        turnos.offer("Ana");
        turnos.offer("Luis");
        turnos.offer("Zoe");

        while (!turnos.isEmpty()) {
            String actual = turnos.poll();
            System.out.println("Atendiendo a " + actual);
        }
    }
}