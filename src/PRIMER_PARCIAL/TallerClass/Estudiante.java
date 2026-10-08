package TallerClass;

public class Estudiante {

    private String nombre;
    private double[] calificaciones;
    private int tope;
    private int capacidadMaxima;

    public Estudiante(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidadMaxima = capacidad;
        this.calificaciones = new double[capacidad];
        this.tope = 0;
    }

    public boolean registrarCalificacion(double nota) {
        if (tope >= capacidadMaxima || nota < 0 || nota > 10) {
            return false;
        }
        calificaciones[tope++] = nota;
        return true;
    }

    public double calcularPromedio() {
        if (tope == 0) return 0.0;
        double suma = 0;
        for (int i = 0; i < tope; i++) {
            suma += calificaciones[i];
        }
        return suma / tope;
    }

    public String getNombre() {
        return nombre;
    }
    
}
