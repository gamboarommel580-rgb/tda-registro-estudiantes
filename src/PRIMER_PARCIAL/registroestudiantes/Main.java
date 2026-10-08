package registroestudiantes;

import registroestudiantes.model.Estudiante;
import registroestudiantes.tda.RegistroEstudiantes;
import registroestudiantes.ui.Menu;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final RegistroEstudiantes registro = new RegistroEstudiantes();
    private static final Menu menu = new Menu(scanner, registro);

    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("SISTEMA DE REGISTRO DE ESTUDIANTES - CURSO 3B");
        System.out.println("TDA Estatico - Capacidad: " + registro.getCapacidad() + " estudiantes");
        System.out.println("=".repeat(80));

        registrarEstudiantesEjemplo();
        ejecutarPruebas();

        int opcion;
        do {
            menu.mostrar();
            opcion = leerOpcion();
            procesarOpcion(opcion);
        } while (opcion != 0);

        scanner.close();
        System.out.println("\nGracias por usar el sistema!");
    }

    private static void registrarEstudiantesEjemplo() {
        System.out.println("\nRegistrando estudiantes del curso 3B...");
        Estudiante[] ejemplos = {
            new Estudiante(12345678L, "Ana Maria Perez", 20, 8.5),
            new Estudiante(23456789L, "Carlos Jose Gomez", 22, 7.8),
            new Estudiante(34567890L, "Maria Fernanda Lopez", 19, 9.2),
            new Estudiante(45678901L, "Juan Pablo Ruiz", 21, 6.5),
            new Estudiante(56789012L, "Laura Isabel Mora", 23, 9.8),
            new Estudiante(67890123L, "Pedro Antonio Sanchez", 20, 7.5),
            new Estudiante(78901234L, "Sofia Elena Ramirez", 18, 8.9),
            new Estudiante(89012345L, "Miguel Angel Torres", 22, 6.8),
            new Estudiante(90123456L, "Elena Patricia Vega", 21, 9.5),
            new Estudiante(10234567L, "Jorge Luis Mendoza", 20, 7.2)
        };
        for (Estudiante est : ejemplos) {
            registro.registrar(est);
        }
    }

    private static void ejecutarPruebas() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EJECUTANDO PRUEBAS OBLIGATORIAS");
        System.out.println("=".repeat(80));

        System.out.println("\n1. Intentando registrar ID repetido...");
        try {
            Estudiante duplicado = new Estudiante(12345678L, "Test Duplicado", 20, 8.0);
            registro.registrar(duplicado);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n2. Buscando estudiante existente e inexistente...");
        System.out.println("Buscando ID 45678901:");
        Estudiante encontrado = registro.buscarPorId(45678901L);
        System.out.println(encontrado != null ? "Encontrado: " + encontrado : "No encontrado");
        System.out.println("Buscando ID 99999999:");
        encontrado = registro.buscarPorId(99999999L);
        System.out.println(encontrado != null ? "Encontrado: " + encontrado : "No encontrado");

        System.out.println("\n3. Modificando promedio del estudiante ID 23456789...");
        Estudiante est = registro.buscarPorId(23456789L);
        if (est != null) {
            System.out.println("Antes: " + est);
            registro.modificar(23456789L, est.getNombre(), est.getEdad(), 9.5);
            System.out.println("Despues: " + registro.buscarPorId(23456789L));
        }

        System.out.println("\n4. Eliminando estudiante de la mitad...");
        int mitad = registro.getCantidad() / 2;
        Estudiante[] todos = registro.obtenerTodos();
        long idEliminar = todos[mitad].getId();
        System.out.println("Eliminando ID " + idEliminar + " (posicion " + mitad + ")");
        registro.eliminar(idEliminar);

        System.out.println("\n5. Verificando integridad del arreglo...");
        registro.verificarIntegridad();

        System.out.println("\n6. Estado final del arreglo:");
        registro.listarTodos();
    }

    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> menu.registrar();
            case 2 -> menu.listar();
            case 3 -> menu.buscar();
            case 4 -> menu.modificar();
            case 5 -> menu.eliminar();
            case 6 -> menu.verificar();
            case 0 -> {}
            default -> System.out.println("Opcion invalida.");
        }
    }
}