package gestioncolecciondatos.gestor;

import gestioncolecciondatos.model.Estudiante;
import java.util.Arrays;

public class GestorEstudiantes {
    private static final int CAPACIDAD = 20;          
    private final Estudiante[] estudiantes;            
    private int cantidad;                              

    public GestorEstudiantes() {
        this.estudiantes = new Estudiante[CAPACIDAD];
        this.cantidad = 0;
    }

    // --- Métodos  ---

    public boolean estaVacio() { return cantidad == 0; }
    public boolean estaLleno() { return cantidad >= CAPACIDAD; }
    public int getCantidad() { return cantidad; }
    public int getCapacidad() { return CAPACIDAD; }

    public Estudiante[] getEstudiantes() {
        return Arrays.copyOf(estudiantes, cantidad);    
    }

    // ---- Buscar ---

    public Estudiante buscarPorCedula(String cedula) {
        for (int i = 0; i < cantidad; i++) {
            if (estudiantes[i].getCedula().equalsIgnoreCase(cedula)) {
                return estudiantes[i];
            }
        }
        return null;
    }

    public Estudiante buscarPorIndice(int indice) {
        if (indice < 0 || indice >= cantidad) return null;
        return estudiantes[indice];
    }

    public int getIndicePorCedula(String cedula) {
        for (int i = 0; i < cantidad; i++) {
            if (estudiantes[i].getCedula().equalsIgnoreCase(cedula)) {
                return i;
            }
        }
        return -1;
    }

    // ---- Registrar ---

    public boolean registrar(Estudiante estudiante) {
        if (estudiante == null) return false;
        if (estaLleno()) return false;
        if (buscarPorCedula(estudiante.getCedula()) != null) return false;
        
        estudiantes[cantidad++] = estudiante;
        return true;
    }

    // ---- Modificar ---

    public boolean modificar(int indice, String nombres, String apellidos, String fechaNacimiento) {
        if (indice < 0 || indice >= cantidad) return false;
        
        Estudiante est = estudiantes[indice];
        est.setNombres(nombres);
        est.setApellidos(apellidos);
        est.setFechaNacimiento(fechaNacimiento);
        return true;
    }

    // ---- Eliminar ---

    public boolean eliminar(int indice) {
        if (indice < 0 || indice >= cantidad) return false;
        
        for (int i = indice; i < cantidad - 1; i++) {
            estudiantes[i] = estudiantes[i + 1];
        }
        estudiantes[--cantidad] = null;
        return true;
    }

    // --- Cálculos de Promedios ---

    public boolean tieneNotasRegistradas() {
        for (int i = 0; i < cantidad; i++) {
            if (estudiantes[i].getNumNotas() > 0) return true;
        }
        return false;
    }

    public double calcularPromedioGeneral() {
        if (cantidad == 0) return 0.0;
        
        double sumaPromedios = 0.0;
        int estudiantesConNotas = 0;

        for (int i = 0; i < cantidad; i++) {
            if (estudiantes[i].getNumNotas() > 0) {
                sumaPromedios += estudiantes[i].calcularPromedio();
                estudiantesConNotas++;
            }
        }
        return estudiantesConNotas > 0 ? sumaPromedios / estudiantesConNotas : 0.0;
    }
}