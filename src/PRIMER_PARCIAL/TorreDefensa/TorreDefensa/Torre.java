import java.awt.Color;

/**
 * Clase Torre: representa una torre colocada por el jugador en el mapa.
 * Cada torre tiene una posición fija (columna, fila), un rango de ataque
 * (en celdas), un daño por quantum y un nivel que sube al mejorarla.
 */
public class Torre {

    public static final int TIPO_BASICA = 1;
    public static final int TIPO_ALCANCE = 2;
    public static final int TIPO_PESADA = 3;

    private int tipo;
    private String nombre;
    private int columna;
    private int fila;
    private int rango;       // en celdas
    private int dano;        // daño actual por quantum
    private int danoBase;    // daño con el que fue creada (nivel 1)
    private int nivel;
    private int costoBase;
    private Color color;

    public Torre(int tipo, int columna, int fila) {
        this.tipo = tipo;
        this.columna = columna;
        this.fila = fila;
        this.nivel = 1;

        if (tipo == TIPO_BASICA) {
            this.nombre = "Basica";
            this.rango = 2;
            this.danoBase = 15;
            this.costoBase = 50;
            this.color = new Color(40, 80, 200);
        } else if (tipo == TIPO_ALCANCE) {
            this.nombre = "Alcance";
            this.rango = 4;
            this.danoBase = 8;
            this.costoBase = 70;
            this.color = new Color(40, 160, 60);
        } else {
            this.nombre = "Pesada";
            this.rango = 1;
            this.danoBase = 30;
            this.costoBase = 100;
            this.color = new Color(150, 40, 150);
        }
        this.dano = this.danoBase;
    }

    // Costo de mejorar la torre a su siguiente nivel.
    public int getCostoMejora() {
        return costoBase / 2;
    }

    // Aplica la mejora: sube el nivel y el daño.
    public void mejorar() {
        nivel = nivel + 1;
        dano = dano + (danoBase / 2);
    }

    // Revierte la torre a un nivel y daño anteriores (usado por Deshacer).
    public void restaurarNivel(int nivelAnterior, int danoAnterior) {
        this.nivel = nivelAnterior;
        this.dano = danoAnterior;
    }

    // Determina si una celda (columnaObjetivo, filaObjetivo) está dentro del rango de la torre.
    public boolean enRango(int columnaObjetivo, int filaObjetivo) {
        int dx = columnaObjetivo - columna;
        int dy = filaObjetivo - fila;
        double distancia = Math.sqrt((dx * dx) + (dy * dy));
        return distancia <= rango;
    }

    public int getColumna() {
        return columna;
    }

    public int getFila() {
        return fila;
    }

    public int getRango() {
        return rango;
    }

    public int getDano() {
        return dano;
    }

    public int getDanoBase() {
        return danoBase;
    }

    public int getNivel() {
        return nivel;
    }

    public int getCostoBase() {
        return costoBase;
    }

    public String getNombre() {
        return nombre;
    }

    public Color getColor() {
        return color;
    }
}
