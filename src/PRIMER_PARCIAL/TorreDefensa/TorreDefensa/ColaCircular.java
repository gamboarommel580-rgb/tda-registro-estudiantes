/**
 * Clase ColaCircular: implementación propia (con arreglo) de una cola
 * circular de objetos Cozy. Se usa para actualizar, en cada quantum de
 * tiempo, el estado (PV, posición) de cada enemigo que está activo en
 * la ruta.
 *
 * Funciona con los índices "frente" y "final" que "dan la vuelta" al
 * llegar al final del arreglo (por eso es "circular").
 */
public class ColaCircular {

    private Cozy[] elementos;
    private int frente;
    private int finCola;
    private int cantidad;
    private int capacidad;

    public ColaCircular(int capacidad) {
        this.capacidad = capacidad;
        this.elementos = new Cozy[capacidad];
        this.frente = 0;
        this.finCola = -1;
        this.cantidad = 0;
    }

    public boolean estaVacia() {
        return cantidad == 0;
    }

    public boolean estaLlena() {
        return cantidad == capacidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    // Agrega un enemigo al final de la cola.
    public void encolar(Cozy c) {
        if (estaLlena()) {
            return; // cola llena, se ignora (no debería pasar en este juego)
        }
        finCola = (finCola + 1) % capacidad;
        elementos[finCola] = c;
        cantidad = cantidad + 1;
    }

    // Saca y devuelve el enemigo que está al frente de la cola.
    public Cozy desencolar() {
        if (estaVacia()) {
            return null;
        }
        Cozy c = elementos[frente];
        elementos[frente] = null;
        frente = (frente + 1) % capacidad;
        cantidad = cantidad - 1;
        return c;
    }

    // Devuelve un arreglo con todos los enemigos activos, en orden,
    // sin sacarlos de la cola. Se usa solo para dibujarlos en pantalla.
    public Cozy[] obtenerTodos() {
        Cozy[] copia = new Cozy[cantidad];
        int indiceLectura = frente;
        for (int i = 0; i < cantidad; i++) {
            copia[i] = elementos[indiceLectura];
            indiceLectura = (indiceLectura + 1) % capacidad;
        }
        return copia;
    }
}
