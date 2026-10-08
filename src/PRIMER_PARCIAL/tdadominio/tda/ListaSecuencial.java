package tdadominio.tda;

import java.lang.reflect.Array;
import java.util.function.Predicate;

public class ListaSecuencial<T extends Comparable<T>> {
    private T[] elementos;
    private int cantidad;
    private final Class<T> claseTipo;

    @SuppressWarnings("unchecked")
    public ListaSecuencial(Class<T> claseTipo, int capacidadInicial) {
        this.claseTipo = claseTipo;
        this.elementos = (T[]) Array.newInstance(claseTipo, capacidadInicial);
        this.cantidad = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getCapacidad() {
        return elementos.length;
    }

    public T obtener(int posicion) {
        if (posicion < 0 || posicion >= cantidad) {
            throw new IndexOutOfBoundsException("Posición fuera de rango.");
        }
        return elementos[posicion];
    }

    public void insertar(T elemento, int posicion) {
        if (posicion < 0 || posicion > cantidad) {
            throw new IndexOutOfBoundsException("Posición inválida para inserción.");
        }
        asegurarCapacidad();
        System.arraycopy(elementos, posicion, elementos, posicion + 1, cantidad - posicion);
        elementos[posicion] = elemento;
        cantidad++;
    }

    public void insertarOrdenado(T elemento) {
        asegurarCapacidad();
        int pos = 0;
        while (pos < cantidad && elementos[pos].compareTo(elemento) < 0) {
            pos++;
        }
        insertar(elemento, pos);
    }

    public ListaSecuencial<T> buscar(Predicate<T> criterio) {
        ListaSecuencial<T> resultado = new ListaSecuencial<>(claseTipo, cantidad > 0 ? cantidad : 1);
        for (int i = 0; i < cantidad; i++) {
            if (criterio.test(elementos[i])) {
                resultado.insertar(elementos[i], resultado.cantidad);
            }
        }
        return resultado;
    }

    @SuppressWarnings("unchecked")
    private void asegurarCapacidad() {
        if (cantidad >= elementos.length) {
            int nuevaCapacidad = elementos.length == 0 ? 4 : elementos.length * 2;
            T[] nuevoArreglo = (T[]) Array.newInstance(claseTipo, nuevaCapacidad);
            System.arraycopy(elementos, 0, nuevoArreglo, 0, cantidad);
            elementos = nuevoArreglo;
        }
    }
}