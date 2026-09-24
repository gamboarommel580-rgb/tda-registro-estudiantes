package Implementar_Pila;

import java.util.ArrayDeque;
import java.util.Deque;

public class PilaNativa<T> {

    // Se utiliza Deque en lugar de la clase legada Stack según las recomendaciones de Java
    private final Deque<T> pila;

    public PilaNativa() {
        this.pila = new ArrayDeque<>();
    }

    public void apilar(T elemento) {
        pila.push(elemento);
    }

    public T desapilar() {
        if (pila.isEmpty()) {
            throw new IllegalStateException("La pila está vacía.");
        }
        return pila.pop();
    }

    public T verCima() {
        if (pila.isEmpty()) {
            throw new IllegalStateException("La pila está vacía.");
        }
        return pila.peek();
    }

    public boolean estaVacia() {
        return pila.isEmpty();
    }

    public int getTamano() {
        return pila.size();
    }
}