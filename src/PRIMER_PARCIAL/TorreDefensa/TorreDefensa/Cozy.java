import java.awt.Color;

/**
 * Clase Cozy: representa a un ENEMIGO dentro del juego.
 * Cada Cozy avanza celda por celda sobre la ruta predefinida
 * y tiene Puntos de Vida (PV) que bajan cuando una torre le hace daño.
 */
public class Cozy {

    private int columnaActual;
    private int filaActual;
    private int indiceRuta;   // posición dentro del arreglo "ruta"
    private int pvActual;
    private int pvMaximo;
    private int oroAlMorir;   // oro que da al jugador si es destruido
    private Color color;

    public Cozy(int pvMaximo, int oroAlMorir, int[][] ruta) {
        this.pvMaximo = pvMaximo;
        this.pvActual = pvMaximo;
        this.oroAlMorir = oroAlMorir;
        this.indiceRuta = 0;
        this.columnaActual = ruta[0][0];
        this.filaActual = ruta[0][1];
        this.color = new Color(200, 30, 30);
    }

    // Aplica daño recibido de las torres. Los PV nunca bajan de 0.
    public void recibirDano(int dano) {
        pvActual -= dano;
        if (pvActual < 0) {
            pvActual = 0;
        }
    }

    public boolean estaVivo() {
        return pvActual > 0;
    }

    /**
     * Avanza una celda dentro de la ruta.
     * Devuelve true si el enemigo llegó al final de la ruta
     * (es decir, alcanzó la fuente de energía).
     */
    public boolean avanzar(int[][] ruta) {
        indiceRuta = indiceRuta + 1;
        if (indiceRuta >= ruta.length) {
            return true;
        }
        columnaActual = ruta[indiceRuta][0];
        filaActual = ruta[indiceRuta][1];
        return false;
    }

    public int getColumnaActual() {
        return columnaActual;
    }

    public int getFilaActual() {
        return filaActual;
    }

    public int getPvActual() {
        return pvActual;
    }

    public int getPvMaximo() {
        return pvMaximo;
    }

    public int getOroAlMorir() {
        return oroAlMorir;
    }

    public Color getColor() {
        return color;
    }
}
