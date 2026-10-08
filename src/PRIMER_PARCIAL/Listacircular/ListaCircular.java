package Listacircular;

public class ListaCircular<T> {

    private Nodo<T> ultimo;
    private int     tamanio;

    public ListaCircular() {
        this.ultimo  = null;
        this.tamanio = 0;
    }

    public boolean estaVacia() {
        return ultimo == null;
    }

    public int contarElementos() {
        return tamanio;
    }

    public void insertarAlInicio(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (estaVacia()) {
            nuevo.siguiente = nuevo;
            ultimo = nuevo;
        } else {
            nuevo.siguiente = ultimo.siguiente;
            ultimo.siguiente = nuevo;
        }
        tamanio++;
    }

    public void insertarAlFinal(T dato) {
        insertarAlInicio(dato);
        ultimo = ultimo.siguiente;
    }

    public void insertarEnPosicion(T dato, int posicion) {
        if (posicion < 0 || posicion > tamanio)
            throw new IndexOutOfBoundsException("Posicion invalida: " + posicion);
        if (posicion == 0)       { insertarAlInicio(dato); return; }
        if (posicion == tamanio) { insertarAlFinal(dato);  return; }

        Nodo<T> nuevo    = new Nodo<>(dato);
        Nodo<T> anterior = ultimo.siguiente;
        for (int i = 0; i < posicion - 1; i++)
            anterior = anterior.siguiente;
        nuevo.siguiente    = anterior.siguiente;
        anterior.siguiente = nuevo;
        tamanio++;
    }

    public boolean eliminarPorPosicion(int posicion) {
        if (estaVacia() || posicion < 0 || posicion >= tamanio) return false;
        if (tamanio == 1) { ultimo = null; tamanio--; return true; }

        Nodo<T> anterior = ultimo;
        for (int i = 0; i < posicion; i++)
            anterior = anterior.siguiente;

        Nodo<T> aEliminar  = anterior.siguiente;
        anterior.siguiente = aEliminar.siguiente;
        if (aEliminar == ultimo) ultimo = anterior;
        tamanio--;
        return true;
    }

    public boolean eliminarPorValor(T valor) {
        if (estaVacia()) return false;

        Nodo<T> anterior = ultimo;
        Nodo<T> actual   = ultimo.siguiente;

        for (int i = 0; i < tamanio; i++) {
            if (actual.dato.equals(valor)) {
                if (tamanio == 1) {
                    ultimo = null;
                } else {
                    anterior.siguiente = actual.siguiente;
                    if (actual == ultimo) ultimo = anterior;
                }
                tamanio--;
                return true;
            }
            anterior = actual;
            actual   = actual.siguiente;
        }
        return false;
    }

    public Nodo<T> getPrimero() {
        return estaVacia() ? null : ultimo.siguiente;
    }

    public Nodo<T> getUltimo() {
        return ultimo;
    }

    public void mostrarTodos() {
        if (estaVacia()) { System.out.println("  [Lista vacia]"); return; }
        Nodo<T> actual = ultimo.siguiente;
        StringBuilder sb = new StringBuilder("  ");
        for (int i = 0; i < tamanio; i++) {
            sb.append(actual.dato);
            if (i < tamanio - 1) sb.append(" -> ");
            actual = actual.siguiente;
        }
        sb.append(" -> (regresa al inicio)");
        System.out.println(sb);
    }
}