package tdadominio;

import tdadominio.modelo.Estudiante;
import tdadominio.servicio.GestorEstudiantes;
import tdadominio.tda.ListaSecuencial;
import tdadominio.ui.ConsolaUI;

public class Main {
    public static void main(String[] args) {
        int capacidad = ConsolaUI.leerEnteroEnRango("Ingrese capacidad inicial de la lista: ", 1, 100);
        GestorEstudiantes gestor = new GestorEstudiantes(capacidad);

        int opción;
        do {
            mostrarMenu();
            opción = ConsolaUI.leerEnteroEnRango("Seleccione una opción: ", 1, 8);
            ejecutarOpcion(opción, gestor);
        } while (opción != 8);
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENU TDA LISTA SECUENCIAL ---");
        System.out.println("1. Insertar estudiante ordenadamente (por cédula)");
        System.out.println("2. Insertar estudiante en posición específica");
        System.out.println("3. Obtener estudiante por posición");
        System.out.println("4. Contar aprobados");
        System.out.println("5. Buscar por nombre");
        System.out.println("6. Mostrar estudiante con mayor promedio");
        System.out.println("7. Simulación en papel (Promedio manual)");
        System.out.println("8. Salir");
    }

    private static void ejecutarOpcion(int opcion, GestorEstudiantes gestor) {
        switch (opcion) {
            case 1 -> {
                String cedula = ConsolaUI.leerTexto("Cédula: ");
                String nombre = ConsolaUI.leerTexto("Nombre: ");
                double promedio = ConsolaUI.leerDoubleEnRango("Promedio (0 - 10): ", 0.0, 10.0);
                gestor.agregarEstudianteOrdenado(cedula, nombre, promedio);
                System.out.println("Estudiante insertado ordenadamente.");
            }
            case 2 -> {
                String cedula = ConsolaUI.leerTexto("Cédula: ");
                String nombre = ConsolaUI.leerTexto("Nombre: ");
                double promedio = ConsolaUI.leerDoubleEnRango("Promedio (0 - 10): ", 0.0, 10.0);
                int pos = ConsolaUI.leerEnteroEnRango("Posición: ", 0, gestor.getLista().getCantidad());
                gestor.insertarEnPosicion(cedula, nombre, promedio, pos);
                System.out.println("Estudiante insertado en posición " + pos);
            }
            case 3 -> {
                if (gestor.getLista().getCantidad() == 0) {
                    System.out.println("La lista está vacía.");
                    return;
                }
                int pos = ConsolaUI.leerEnteroEnRango("Posición a consultar: ", 0, gestor.getLista().getCantidad() - 1);
                System.out.println("Estudiante: " + gestor.getLista().obtener(pos));
            }
            case 4 -> {
                double minimo = ConsolaUI.leerDoubleEnRango("Ingrese nota mínima de aprobación: ", 0.0, 10.0);
                System.out.println("Cantidad de aprobados: " + gestor.contarAprobados(minimo));
            }
            case 5 -> {
                String nombre = ConsolaUI.leerTexto("Nombre a buscar: ");
                ListaSecuencial<Estudiante> resultados = gestor.buscarPorNombre(nombre);
                if (resultados.getCantidad() == 0) {
                    System.out.println("No se encontraron coincidencias.");
                } else {
                    for (int i = 0; i < resultados.getCantidad(); i++) {
                        System.out.println(resultados.obtener(i));
                    }
                }
            }
            case 6 -> {
                Estudiante mayor = gestor.obtenerMayorPromedio();
                if (mayor == null) {
                    System.out.println("La lista está vacía.");
                } else {
                    System.out.println("Estudiante con mayor promedio: " + mayor);
                }
            }
            case 7 -> {
                int n = ConsolaUI.leerEnteroEnRango("Ingrese cantidad de notas a promediar: ", 1, 50);
                double[] notas = new double[n];
                for (int i = 0; i < n; i++) {
                    notas[i] = ConsolaUI.leerDoubleEnRango("Nota " + (i + 1) + ": ", 0.0, 10.0);
                }
                double prom = GestorEstudiantes.calcularPromedioNotas(notas);
                System.out.printf("El promedio calculado es: %.2f\n", prom);
            }
            case 8 -> System.out.println("Saliendo del programa...");
        }
    }
}
