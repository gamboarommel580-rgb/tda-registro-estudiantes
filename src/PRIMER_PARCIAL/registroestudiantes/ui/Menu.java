package registroestudiantes.ui;

import registroestudiantes.model.Estudiante;
import registroestudiantes.tda.RegistroEstudiantes;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private final RegistroEstudiantes registro;

    public Menu(Scanner scanner, RegistroEstudiantes registro) {
        this.scanner = scanner;
        this.registro = registro;
    }

    public void mostrar() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SISTEMA DE REGISTRO DE ESTUDIANTES - CURSO 3B");
        System.out.println("Capacidad: " + registro.getCapacidad() + " estudiantes");
        System.out.println("=".repeat(80));
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Listar estudiantes");
        System.out.println("3. Buscar estudiante por ID");
        System.out.println("4. Modificar estudiante");
        System.out.println("5. Eliminar estudiante");
        System.out.println("6. Verificar integridad del arreglo");
        System.out.println("0. Salir");
        System.out.println("=".repeat(80));
        System.out.print("Seleccione una opcion: ");
    }

    public void registrar() {
        if (registro.estaLleno()) {
            System.out.println("Error: Capacidad maxima alcanzada.");
            return;
        }
        System.out.println("\n--- REGISTRAR ESTUDIANTE ---");
        try {
            long id = leerLong("ID (8-10 digitos): ");
            if (registro.buscarPorId(id) != null) {
                System.out.println("Error: ID ya registrado.");
                return;
            }
            String nombre = leerString("Nombre: ");
            int edad = leerInt("Edad (15-100): ", 15, 100);
            double promedio = leerDouble("Promedio (0.0-10.0): ", 0.0, 10.0);
            Estudiante nuevo = new Estudiante(id, nombre, edad, promedio);
            registro.registrar(nuevo);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void listar() {
        registro.listarTodos();
    }

    public void buscar() {
        if (registro.estaVacio()) {
            System.out.println("El registro esta vacio.");
            return;
        }
        long id = leerLong("ID a buscar: ");
        Estudiante est = registro.buscarPorId(id);
        if (est != null) {
            System.out.println("Estudiante encontrado:");
            System.out.println(est);
        } else {
            System.out.println("No encontrado.");
        }
    }

    public void modificar() {
        if (registro.estaVacio()) {
            System.out.println("El registro esta vacio.");
            return;
        }
        long id = leerLong("ID a modificar: ");
        Estudiante est = registro.buscarPorId(id);
        if (est == null) {
            System.out.println("No encontrado.");
            return;
        }
        System.out.println("Datos actuales: " + est);
        System.out.println("\nIngrese nuevos datos (Enter para mantener):");
        String nombre = leerStringOpcional("Nuevo nombre: ", est.getNombre());
        int edad = leerIntOpcional("Nueva edad (15-100): ", est.getEdad(), 15, 100);
        double promedio = leerDoubleOpcional("Nuevo promedio (0.0-10.0): ", est.getPromedio(), 0.0, 10.0);
        registro.modificar(id, nombre, edad, promedio);
    }

    public void eliminar() {
        if (registro.estaVacio()) {
            System.out.println("El registro esta vacio.");
            return;
        }
        long id = leerLong("ID a eliminar: ");
        Estudiante est = registro.buscarPorId(id);
        if (est == null) {
            System.out.println("No encontrado.");
            return;
        }
        System.out.println("Estudiante a eliminar: " + est);
        System.out.print("Confirmar (s/N): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (confirm.equals("s") || confirm.equals("si")) {
            registro.eliminar(id);
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    public void verificar() {
        registro.verificarIntegridad();
    }

    private String leerString(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("El campo no puede estar vacio.");
        }
    }

    private String leerStringOpcional(String mensaje, String actual) {
        System.out.print(mensaje);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? actual : input;
    }

    private long leerLong(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                long valor = Long.parseLong(scanner.nextLine().trim());
                if (valor >= 10000000L && valor <= 9999999999L) return valor;
                System.out.println("El ID debe tener entre 8 y 10 digitos.");
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero valido.");
            }
        }
    }

    private int leerInt(String mensaje, int min, int max) {
        while (true) {
            try {
                System.out.print(mensaje);
                int valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor >= min && valor <= max) return valor;
                System.out.println("El valor debe estar entre " + min + " y " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero entero.");
            }
        }
    }

    private int leerIntOpcional(String mensaje, int actual, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return actual;
            try {
                int valor = Integer.parseInt(input);
                if (valor >= min && valor <= max) return valor;
                System.out.println("El valor debe estar entre " + min + " y " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero entero.");
            }
        }
    }

    private double leerDouble(String mensaje, double min, double max) {
        while (true) {
            try {
                System.out.print(mensaje);
                double valor = Double.parseDouble(scanner.nextLine().trim());
                if (valor >= min && valor <= max) return valor;
                System.out.println("El valor debe estar entre " + min + " y " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero decimal.");
            }
        }
    }

    private double leerDoubleOpcional(String mensaje, double actual, double min, double max) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return actual;
            try {
                double valor = Double.parseDouble(input);
                if (valor >= min && valor <= max) return valor;
                System.out.println("El valor debe estar entre " + min + " y " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero decimal.");
            }
        }
    }
}