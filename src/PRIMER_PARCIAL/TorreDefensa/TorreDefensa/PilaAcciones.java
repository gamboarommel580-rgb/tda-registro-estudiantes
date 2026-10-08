import java.util.ArrayList;

/**
 * Clase PilaAcciones: pila (LIFO) que guarda el historial de acciones
 * (AccionTorre) para poder implementar Deshacer y Rehacer.
 * La última acción que entra es la primera que sale.
 */
public class PilaAcciones {

    private ArrayList<AccionTorre> elementos;

    public PilaAcciones() {
        elementos = new ArrayList<AccionTorre>();
    }

    public boolean estaVacia() {
        return elementos.isEmpty();
    }

    // Apilar: agrega una acción en el tope de la pila.
    public void apilar(AccionTorre accion) {
        elementos.add(accion);
    }

    // Desapilar: saca y devuelve la acción que está en el tope de la pila.
    public AccionTorre desapilar() {
        if (estaVacia()) {
            return null;
        }
        int ultimoIndice = elementos.size() - 1;
        return elementos.remove(ultimoIndice);
    }

    // Vacía por completo la pila (se usa cuando el jugador hace una
    // acción nueva después de haber deshecho algo).
    public void vaciar() {
        elementos.clear();
    }

    public int getCantidad() {
        return elementos.size();
    }
}
