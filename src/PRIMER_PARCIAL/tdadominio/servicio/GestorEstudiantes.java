package tdadominio.servicio;

import tdadominio.modelo.Estudiante;
import tdadominio.tda.ListaSecuencial;

public class GestorEstudiantes {
    private final ListaSecuencial<Estudiante> lista;

    public GestorEstudiantes(int capacidadInicial) {
        this.lista = new ListaSecuencial<>(Estudiante.class, capacidadInicial);
    }

    public ListaSecuencial<Estudiante> getLista() {
        return lista;
    }

    public void agregarEstudianteOrdenado(String cedula, String nombre, double promedio) {
        lista.insertarOrdenado(new Estudiante(cedula, nombre, promedio));
    }

    public void insertarEnPosicion(String cedula, String nombre, double promedio, int posicion) {
        lista.insertar(new Estudiante(cedula, nombre, promedio), posicion);
    }

    public int contarAprobados(double minimo) {
        int contador = 0;
        for (int i = 0; i < lista.getCantidad(); i++) {
            if (lista.obtener(i).getPromedio() >= minimo) {
                contador++;
            }
        }
        return contador;
    }

    public ListaSecuencial<Estudiante> buscarPorNombre(String nombre) {
        return lista.buscar(e -> e.getNombre().equalsIgnoreCase(nombre));
    }

    public Estudiante obtenerMayorPromedio() {
        if (lista.getCantidad() == 0) {
            return null;
        }
        Estudiante mayor = lista.obtener(0);
        for (int i = 1; i < lista.getCantidad(); i++) {
            if (lista.obtener(i).getPromedio() > mayor.getPromedio()) {
                mayor = lista.obtener(i);
            }
        }
        return mayor;
    }

    public static double calcularPromedioNotas(double[] notas) {
        double suma = 0;
        for (double nota : notas) {
            suma += nota;
        }
        return notas.length == 0 ? 0 : suma / notas.length;
    }

    
}
