package gestioncolecciondatos.model;

import java.util.Arrays;

public class Estudiante {
    private static final int MAX_NOTAS = 7;
    
    private final String cedula;        
    private String nombres;
    private String apellidos;
    private String fechaNacimiento;
    private final double[] notas;       
    private int numNotas;               

    public Estudiante(String cedula, String nombres, String apellidos, String fechaNacimiento) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.notas = new double[MAX_NOTAS];
        this.numNotas = 0;
    }

    // Getters
    public String getCedula() { return cedula; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public int getNumNotas() { return numNotas; }
    public int getMaxNotas() { return MAX_NOTAS; }

    public double[] getNotas() { 
        return Arrays.copyOf(notas, numNotas);  
    }

    public double getNota(int indice) {
        if (indice < 0 || indice >= numNotas) return -1.0;
        return notas[indice];
    }

    // Setters
    public void setNombres(String nombres) { this.nombres = nombres; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    // --- CRUD de Notas ---

    public boolean agregarNota(double nota) {
        if (numNotas >= MAX_NOTAS) return false;
        notas[numNotas++] = nota;
        return true;
    }

    public boolean modificarNota(int indice, double nota) {
        if (indice < 0 || indice >= numNotas) return false;
        notas[indice] = nota;
        return true;
    }

    public boolean eliminarNota(int indice) {
        if (indice < 0 || indice >= numNotas) return false;
        for (int i = indice; i < numNotas - 1; i++) {
            notas[i] = notas[i + 1];
        }
        numNotas--;
        return true;
    }

    // --- Cálculos ---

    public double calcularPromedio() {
        if (numNotas == 0) return 0.0;
        double suma = 0.0;
        for (int i = 0; i < numNotas; i++) {
            suma += notas[i];
        }
        return suma / numNotas;
    }

    // --- Formateo de datos para UI ---

    @Override
    public String toString() {
        return String.format("Cédula: %-10s | Nombres: %-15s | Apellidos: %-15s | F.Nac: %-12s",
                cedula, nombres, apellidos, fechaNacimiento);
    }

    public String notasToString() {
        if (numNotas == 0) return "[ Sin calificaciones ]";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numNotas; i++) {
            sb.append(String.format("[%.1f] ", notas[i]));
        }
        sb.append(String.format("| Promedio: %.2f", calcularPromedio()));
        return sb.toString();
    }
}