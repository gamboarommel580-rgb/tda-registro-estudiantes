package tdadominio.modelo;

public class Estudiante implements Comparable<Estudiante>  {
    private String cedula;
    private String nombre;
    private double promedio;

    public Estudiante(String cedula, String nombre, double promedio) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.promedio = promedio;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPromedio() {
        return promedio;
    }

    @Override
    public int compareTo(Estudiante otro) {
        return this.cedula.compareTo(otro.cedula);
    }

    @Override
    public String toString() {
        return String.format("Cédula: %s | Nombre: %s | Promedio: %.2f", cedula, nombre, promedio);
    }
    
}
