package Ejercicio3;

public class Estudiante {

    private String cedula;
    private String nombre;
    private double promedio;

    public Estudiante(String cedula, String nombre, double promedio) {
        this.cedula = cedula;
        this.nombre = nombre;
        actualizarPromedio(promedio);
    }

    public void actualizarPromedio(double nuevoPromedio) {
        if (nuevoPromedio >= 0 && nuevoPromedio <= 10) {
            promedio = nuevoPromedio;
        }
    }

    public boolean aprueba() {
        return promedio >= 7;
    }

    public String mostrarResumen() {
        return cedula + " - " + nombre + " - Promedio: " + promedio;
    }

    public double getPromedio() {
        return promedio;
    }
    
}
