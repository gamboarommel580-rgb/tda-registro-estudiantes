package Listacircular;

public class Proceso {
    private final String nombre;
    private int          tiempoRestante;

    public Proceso(String nombre, int tiempoRestante) {
        this.nombre         = nombre;
        this.tiempoRestante = tiempoRestante;
    }

    public String getNombre()         { return nombre; }
    public int    getTiempoRestante() { return tiempoRestante; }

    public void ejecutar(int quantum) {
        tiempoRestante = Math.max(0, tiempoRestante - quantum);
    }

    public boolean haTerminado() { return tiempoRestante == 0; }

    @Override
    public String toString() {
        return nombre + "(" + tiempoRestante + ")";
    }
}