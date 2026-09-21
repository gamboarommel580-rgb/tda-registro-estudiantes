package Implementar_Pila;

public class PilaNodos<T> {

    // Clase interna para gestionar cada nodo de la pila
    private static class Nodo<T> {
        T valor;
        Nodo<T> siguiente;

        Nodo(T valor) {
            this.valor = valor;
            this.siguiente = null;
        }
    }

    private Nodo<T> cima;
    private int tamano;

    public PilaNodos() {
        this.cima = null;
        this.tamano = 0;
    }

    // Operación Push: Inserta en O(1) al inicio
    public void apilar(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        nuevo.siguiente = cima;
        cima = nuevo;
        tamano++;
    }

    // Operación Pop: Remueve la cima en O(1)
    public T desapilar() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía.");
        }
        T valor = cima.valor;
        cima = cima.siguiente;
        tamano--;
        return valor;
    }

    // Operación Peek: Consulta la cima sin remover
    public T verCima() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía.");
        }
        return cima.valor;
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public int getTamano() {
        return tamano;
    }
}