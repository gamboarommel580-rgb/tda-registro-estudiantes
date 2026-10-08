package gestioncolecciondatos;

import gestioncolecciondatos.gestor.GestorEstudiantes;
import gestioncolecciondatos.model.Estudiante;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final GestorEstudiantes gestor = new GestorEstudiantes();

    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("     GESTOR DE ESTUDIANTES - APE1");
        System.out.println("     Cupo máximo: " + gestor.getCapacidad() + " estudiantes");
        System.out.println("     Máximo notas por estudiante: 7");
        System.out.println("=".repeat(60));

        cargarDatosPrueba();

        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Teclee su opción (0-4): ");
            procesarOpcionPrincipal(opcion);
        } while (opcion != 0);

        scanner.close();
        System.out.println("\n¡Gracias por usar el sistema!");
    }


    private static void cargarDatosPrueba() {
        System.out.println("\n Cargando estudiantes de ejemplo...");
        Estudiante[] ejemplos = {
            new Estudiante("1234567890", "Ana María", "Pérez García", "15/03/2000"),
            new Estudiante("0987654321", "Carlos José", "Gómez López", "22/07/1999"),
            new Estudiante("1112223334", "María Fernanda", "López Martínez", "10/11/2001"),
            new Estudiante("4445556667", "Juan Pablo", "Ruiz Torres", "05/09/2000"),
            new Estudiante("7778889990", "Laura Isabel", "Mora Santos", "30/06/1998")
        };
        for (Estudiante est : ejemplos) {
            gestor.registrar(est);
        }
        System.out.println( + ejemplos.length + " estudiantes registrados.");
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("=== GESTOR DE PERSONAS ===");
        System.out.println("=".repeat(60));
        System.out.println("1.- Estudiantes.");
        System.out.println("2.- Registro de calificaciones.");
        System.out.println("3.- Determinar el promedio de notas de un estudiante.");
        System.out.println("4.- Determinar el promedio de notas del curso.");
        System.out.println("0.- Salir");
        System.out.println("=".repeat(60));
    }

    private static void procesarOpcionPrincipal(int opcion) {
        switch (opcion) {
            case 1 -> subMenuEstudiantes();
            case 2 -> subMenuCalificaciones();
            case 3 -> mostrarPromedioEstudiante();
            case 4 -> mostrarPromedioCurso();
            case 0 -> {}
            default -> System.out.println("Opción no válida.");
        }
    }


    private static void subMenuEstudiantes() {
        int opcion;
        do {
            mostrarTablaEstudiantes();
            System.out.println("\n--- SUBMENÚ ESTUDIANTES ---");
            System.out.println("1. Ingresar estudiante (Create)");
            System.out.println("2. Modificar estudiante (Update)");
            System.out.println("3. Eliminar estudiante (Delete)");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> ejecutarIngresoEstudiantes();
                case 2 -> ejecutarModificarEstudiante();
                case 3 -> ejecutarEliminarEstudiante();
                case 0 -> {}
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void mostrarTablaEstudiantes() {
        if (gestor.estaVacio()) {
            System.out.println("\n📋 No hay estudiantes registrados.");
            return;
        }
        System.out.println("\n" + "=".repeat(85));
        System.out.println("📋 LISTADO DE ESTUDIANTES (" + gestor.getCantidad() + "/" + gestor.getCapacidad() + ")");
        System.out.println("=".repeat(85));
        System.out.println("#   Cédula      Nombres            Apellidos          F.Nacimiento");
        System.out.println("-".repeat(85));
        Estudiante[] lista = gestor.getEstudiantes();
        for (int i = 0; i < lista.length; i++) {
            System.out.printf("%-2d. %s%n", (i + 1), lista[i].toString());
        }
        System.out.println("=".repeat(85));
    }


    private static void ejecutarIngresoEstudiantes() {
        boolean continuar = true;
        while (continuar) {
            if (gestor.estaLleno()) {
                System.out.println("Error: Cupo máximo alcanzado (" + gestor.getCapacidad() + ").");
                break;
            }

            System.out.println("\n--- INGRESAR ESTUDIANTE ---");
            String cedula = leerString("Cédula: ");
            
            if (gestor.buscarPorCedula(cedula) != null) {
                System.out.println("Error: Ya existe un estudiante con esa cédula.");
                continuar = solicitarConfirmacion("¿Desea intentar con otra cédula? (s/N): ");
                continue;
            }

            String nombres = leerString("Nombres: ");
            String apellidos = leerString("Apellidos: ");
            String fechaNac = leerString("Fecha de nacimiento (DD/MM/AAAA): ");

            if (gestor.registrar(new Estudiante(cedula, nombres, apellidos, fechaNac))) {
                System.out.println("Estudiante registrado exitosamente.");
                System.out.println("   Total: " + gestor.getCantidad() + "/" + gestor.getCapacidad());
            }

            continuar = solicitarConfirmacion("¿Desea ingresar otro estudiante? (s/N): ");
        }
    }


    private static void ejecutarModificarEstudiante() {
        if (gestor.estaVacio()) {
            System.out.println("📋 No hay estudiantes registrados.");
            return;
        }

        mostrarTablaEstudiantes();
        int indice = leerEntero("Número de autonumérico del estudiante a modificar: ") - 1;
        Estudiante est = gestor.buscarPorIndice(indice);

        if (est == null) {
            System.out.println("Error: Registro no encontrado.");
            return;
        }

        System.out.println("\n Datos actuales:");
        System.out.println(est.toString());

        System.out.println("\nIngrese los nuevos datos (Enter para mantener el valor actual):");
        String nombres = leerStringOpcional("Nuevos Nombres (" + est.getNombres() + "): ", est.getNombres());
        String apellidos = leerStringOpcional("Nuevos Apellidos (" + est.getApellidos() + "): ", est.getApellidos());
        String fechaNac = leerStringOpcional("Nueva Fecha nacimiento (" + est.getFechaNacimiento() + "): ", est.getFechaNacimiento());

        if (gestor.modificar(indice, nombres, apellidos, fechaNac)) {
            System.out.println("Estudiante modificado exitosamente.");
        }
    }


    private static void ejecutarEliminarEstudiante() {
        if (gestor.estaVacio()) {
            System.out.println("No hay estudiantes para eliminar.");
            return;
        }

        mostrarTablaEstudiantes();
        int indice = leerEntero("Número de autonumérico a eliminar: ") - 1;
        Estudiante est = gestor.buscarPorIndice(indice);

        if (est == null) {
            System.out.println("Error: Registro no encontrado.");
            return;
        }

        System.out.println("\nRegistro seleccionado:");
        System.out.println(est.toString());

        if (solicitarConfirmacion("¿Confirmar eliminación? (s/N): ")) {
            if (gestor.eliminar(indice)) {
                System.out.println("Estudiante eliminado.");
                System.out.println("   Total restante: " + gestor.getCantidad() + "/" + gestor.getCapacidad());
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }


    private static void subMenuCalificaciones() {
        if (gestor.estaVacio()) {
            System.out.println("No hay estudiantes registrados. Primero registre estudiantes.");
            return;
        }

        Estudiante est = null;
        boolean continuar = true;
        while (continuar && est == null) {
            String cedula = leerString("Ingrese la cédula del estudiante: ");
            est = gestor.buscarPorCedula(cedula);
            if (est == null) {
                System.out.println("Error: Estudiante no encontrado.");
                continuar = solicitarConfirmacion("¿Desea intentar con otra cédula? (s/N): ");
            }
        }

        if (est == null) return;

        int opcion;
        do {
            System.out.println("\n--- CALIFICACIONES DE ESTUDIANTE ---");
            System.out.println("Estudiante: " + est.getNombres() + " " + est.getApellidos());
            System.out.println("Notas (" + est.getNumNotas() + "/" + est.getMaxNotas() + "): " + est.notasToString());
            System.out.println("\n1. Agregar nota (Create)");
            System.out.println("2. Modificar nota (Update)");
            System.out.println("3. Eliminar nota (Delete)");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> ejecutarAgregarNota(est);
                case 2 -> ejecutarModificarNota(est);
                case 3 -> ejecutarEliminarNota(est);
                case 0 -> {}
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }


    private static void ejecutarAgregarNota(Estudiante est) {
        if (est.getNumNotas() >= est.getMaxNotas()) {
            System.out.println("Se han ingresado todas las calificaciones posibles (" + est.getMaxNotas() + ").");
            return;
        }

        boolean continuar = true;
        while (continuar) {
            double nota = leerDouble("Ingrese nota (0.0-10.0): ", 0.0, 10.0);
            if (est.agregarNota(nota)) {
                System.out.println("Nota registrada.");
                System.out.println("   Notas actuales: " + est.getNumNotas() + "/" + est.getMaxNotas());
                System.out.println("   " + est.notasToString());
            }

            if (est.getNumNotas() >= est.getMaxNotas()) {
                System.out.println("Máximo de notas alcanzado.");
                break;
            }
            continuar = solicitarConfirmacion("¿Desea agregar otra nota? (s/N): ");
        }
    }


    private static void ejecutarModificarNota(Estudiante est) {
        if (est.getNumNotas() == 0) {
            System.out.println("No hay notas registradas.");
            return;
        }

        System.out.println("Notas actuales: " + est.notasToString());
        int pos = leerEntero("Índice de nota a modificar (1-" + est.getNumNotas() + "): ") - 1;

        if (pos < 0 || pos >= est.getNumNotas()) {
            System.out.println(" Error: Índice fuera de rango.");
            return;
        }

        System.out.printf("Nota actual: %.1f%n", est.getNota(pos));
        double nuevaNota = leerDouble("Nueva nota (0.0-10.0): ", 0.0, 10.0);

        if (est.modificarNota(pos, nuevaNota)) {
            System.out.println("Nota actualizada.");
            System.out.println("   " + est.notasToString());
        }
    }


    private static void ejecutarEliminarNota(Estudiante est) {
        if (est.getNumNotas() == 0) {
            System.out.println("No hay notas registradas.");
            return;
        }

        System.out.println("Notas actuales: " + est.notasToString());
        int pos = leerEntero("Índice de nota a eliminar (1-" + est.getNumNotas() + "): ") - 1;

        if (pos < 0 || pos >= est.getNumNotas()) {
            System.out.println("Error: Índice fuera de rango.");
            return;
        }

        if (solicitarConfirmacion("¿Eliminar calificación? (s/N): ")) {
            if (est.eliminarNota(pos)) {
                System.out.println("Nota eliminada.");
                System.out.println("   " + est.notasToString());
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }


    private static void mostrarPromedioEstudiante() {
        if (gestor.estaVacio()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }

        String cedula = leerString("Ingrese la cédula del estudiante: ");
        Estudiante est = gestor.buscarPorCedula(cedula);

        if (est == null) {
            System.out.println("Error: No se encontró un estudiante con la cédula indicada.");
            return;
        }

        System.out.println("\n--- DATOS DEL ESTUDIANTE ---");
        System.out.println("Nombres: " + est.getNombres());
        System.out.println("Apellidos: " + est.getApellidos());
        System.out.println("Fecha de nacimiento: " + est.getFechaNacimiento());
        System.out.println("Notas: " + est.notasToString());
        System.out.printf("Promedio: %.2f%n", est.calcularPromedio());
    }

    private static void mostrarPromedioCurso() {
        System.out.println("\n--- PROMEDIO DEL CURSO ---");

        if (!gestor.tieneNotasRegistradas()) {
            System.out.println("No se han registrado calificaciones de estudiantes.");
            return;
        }

        System.out.printf("Promedio general del curso: %.2f%n", gestor.calcularPromedioGeneral());

        System.out.println("\nDetalle por estudiante:");
        System.out.println("-".repeat(60));
        Estudiante[] estudiantes = gestor.getEstudiantes();
        for (Estudiante est : estudiantes) {
            System.out.printf("%s %s: %.2f%n",
                    est.getNombres(), est.getApellidos(), est.calcularPromedio());
        }
        System.out.println("-".repeat(60));
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada no válida. Ingrese un entero.");
            }
        }
    }

    private static double leerDouble(String mensaje, double min, double max) {
        while (true) {
            try {
                System.out.print(mensaje);
                double val = Double.parseDouble(scanner.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.printf("Valor fuera de rango (%.1f - %.1f).%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Ingrese un decimal.");
            }
        }
    }

    private static String leerString(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String txt = scanner.nextLine().trim();
            if (!txt.isEmpty()) return txt;
            System.out.println("El campo no puede estar vacío.");
        }
    }

    private static String leerStringOpcional(String mensaje, String actual) {
        System.out.print(mensaje);
        String txt = scanner.nextLine().trim();
        return txt.isEmpty() ? actual : txt;
    }

    private static boolean solicitarConfirmacion(String mensaje) {
        System.out.print(mensaje);
        String resp = scanner.nextLine().trim().toLowerCase();
        return resp.equals("s") || resp.equals("si");
    }
}