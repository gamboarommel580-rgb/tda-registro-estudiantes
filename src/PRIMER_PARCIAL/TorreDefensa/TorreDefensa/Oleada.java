/**
 * Clase Oleada: guarda la información de una oleada de enemigos
 * (cuántos Cozy aparecen, con cuánta vida, y cuánto oro dan al morir).
 */
public class Oleada {

    private int numeroOleada;
    private int cantidadEnemigos;
    private int vidaEnemigos;
    private int oroPorEnemigo;

    public Oleada(int numeroOleada, int cantidadEnemigos, int vidaEnemigos, int oroPorEnemigo) {
        this.numeroOleada = numeroOleada;
        this.cantidadEnemigos = cantidadEnemigos;
        this.vidaEnemigos = vidaEnemigos;
        this.oroPorEnemigo = oroPorEnemigo;
    }

    public int getNumeroOleada() {
        return numeroOleada;
    }

    public int getCantidadEnemigos() {
        return cantidadEnemigos;
    }

    public int getVidaEnemigos() {
        return vidaEnemigos;
    }

    public int getOroPorEnemigo() {
        return oroPorEnemigo;
    }
}
