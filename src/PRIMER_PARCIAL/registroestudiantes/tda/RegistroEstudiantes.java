package registroestudiantes.tda;

import registroestudiantes.model.Estudiante;

public class RegistroEstudiantes {
    private static final int CAPACIDAD = 30;
    private final Estudiante[] estudiantes = new Estudiante[CAPACIDAD];
    private int cantidad = 0;

    public boolean estaVacio() { return cantidad == 0; }
    public boolean estaLleno() { return cantidad >= CAPACIDAD; }
    public int getCantidad() { return cantidad; }
    public int getCapacidad() { return CAPACIDAD; }

    private int buscarIndicePorId(long id) {
        for (int i = 0; i < cantidad; i++) {
            if (estudiantes[i].getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public Estudiante buscarPorId(long id) {
        int indice = buscarIndicePorId(id);
        return (indice != -1) ? estudiantes[indice] : null;
    }

    public boolean registrar(Estudiante estudiante) {
        if (estudiante == null) {
            throw new IllegalArgumentException("El estudiante no puede ser null.");
        }
        if (estaLleno()) {
            System.out.println("Error: Registro lleno. Capacidad: " + CAPACIDAD);
            return false;
        }
        if (buscarPorId(estudiante.getId()) != null) {
            System.out.println("Error: ID " + estudiante.getId() + " ya registrado.");
            return false;
        }
        estudiantes[cantidad] = estudiante;
        cantidad++;
        System.out.println("Estudiante registrado. Total: " + cantidad + "/" + CAPACIDAD);
        return true;
    }

    public void listarTodos() {
        if (estaVacio()) {
            System.out.println("El registro esta vacio.");
            return;
        }
        System.out.println("\n" + "=".repeat(80));
        System.out.println("LISTADO DE ESTUDIANTES (" + cantidad + "/" + CAPACIDAD + ")");
        System.out.println("=".repeat(80));
        System.out.println("#   ID           Nombre                    Edad   Promedio");
        System.out.println("-".repeat(80));
        for (int i = 0; i < cantidad; i++) {
            System.out.printf("%-2d. ", (i + 1));
            System.out.println(estudiantes[i]);
        }
        System.out.println("=".repeat(80));
    }

    public Estudiante[] obtenerTodos() {
        Estudiante[] copia = new Estudiante[cantidad];
        System.arraycopy(estudiantes, 0, copia, 0, cantidad);
        return copia;
    }

    public boolean modificar(long id, String nuevoNombre, int nuevaEdad, double nuevoPromedio) {
        int indice = buscarIndicePorId(id);
        if (indice == -1) {
            System.out.println("Error: No existe estudiante con ID " + id);
            return false;
        }
        try {
            estudiantes[indice].actualizarDatos(nuevoNombre, nuevaEdad, nuevoPromedio);
            System.out.println("Estudiante modificado con exito.");
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(long id) {
        if (estaVacio()) {
            System.out.println("Error: El registro esta vacio.");
            return false;
        }
        int indice = buscarIndicePorId(id);
        if (indice == -1) {
            System.out.println("Error: No existe estudiante con ID " + id);
            return false;
        }
        System.out.println("Eliminando estudiante:");
        System.out.println("   " + estudiantes[indice]);
        for (int i = indice; i < cantidad - 1; i++) {
            estudiantes[i] = estudiantes[i + 1];
        }
        cantidad--;
        estudiantes[cantidad] = null;
        System.out.println("Estudiante eliminado. Total: " + cantidad + "/" + CAPACIDAD);
        return true;
    }

    public boolean verificarIntegridad() {
        for (int i = 0; i < cantidad; i++) {
            if (estudiantes[i] == null) {
                System.out.println("Error: Espacio nulo en posicion " + i);
                return false;
            }
        }
        System.out.println("El arreglo es contiguo y no tiene espacios intermedios.");
        return true;
    }
}