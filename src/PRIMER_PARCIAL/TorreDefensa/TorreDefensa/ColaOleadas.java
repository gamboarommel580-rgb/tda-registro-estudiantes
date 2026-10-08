import java.util.ArrayList;

/**
 * Clase ColaOleadas: cola simple (FIFO) que guarda el orden en el que
 * deben aparecer las oleadas de enemigos. La primera oleada que entra
 * es la primera en salir (encolar al final, desencolar del frente).
 */
public class ColaOleadas {

    private ArrayList<Oleada> elementos;

    public ColaOleadas() {
        elementos = new ArrayList<Oleada>();
    }

    public boolean estaVacia() {
        return elementos.isEmpty();
    }

    public void encolar(Oleada o) {
        elementos.add(o);
    }

    public Oleada desencolar() {
        if (estaVacia()) {
            return null;
        }
        return elementos.remove(0);
    }

    public int getCantidad() {
        return elementos.size();
    }
}
