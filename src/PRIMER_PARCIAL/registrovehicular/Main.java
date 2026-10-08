package registrovehicular;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final RegistroVehiculos registro = new RegistroVehiculos();

    // Estructura interna temporal para no repetir codigo (DRY)
    private static class DatosBase {
        String placa, marca, modelo;
        int anio;
        double precio;
        boolean disponible;
    }

    public static void main(String[] args) {
        int opcion = 0;
        do {
            System.out.println("\n=== REGISTRO VEHICULAR TDA ===");
            System.out.println("1. Registrar Automóvil");
            System.out.println("2. Registrar Motocicleta");
            System.out.println("3. Mostrar todos los vehículos (Polimorfismo)");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1 -> registrarVehiculo(true);
                case 2 -> registrarVehiculo(false);
                case 3 -> registro.mostrarTodos();
                case 4 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 4);
    }

    // DRY & KISS: Un solo metodo central para registrar cualquier tipo de vehiculo
    private static void registrarVehiculo(boolean esAutomovil) {
        if (registro.estaLleno()) {
            System.out.println("Error: El registro estático está lleno.");
            return;
        }

        System.out.println(esAutomovil ? "\n--- REGISTRO DE AUTOMÓVIL ---" : "\n--- REGISTRO DE MOTOCICLETA ---");
        DatosBase base = leerDatosBase();

        Vehiculo nuevo;
        if (esAutomovil) {
            int puertas = leerRangoInt("Número de puertas (2 - 6): ", 2, 6);
            boolean electrico = leerBoolean("¿Es eléctrico? (s/n): ");
            nuevo = new Automovil(base.placa, base.marca, base.modelo, base.anio, base.precio, base.disponible, puertas, electrico);
        } else {
            int cilindrada = leerRangoInt("Cilindrada en cc (50 - 2500): ", 50, 2500);
            boolean maletero = leerBoolean("¿Tiene maletero? (s/n): ");
            nuevo = new Motocicleta(base.placa, base.marca, base.modelo, base.anio, base.precio, base.disponible, cilindrada, maletero);
        }

        if (registro.registrar(nuevo)) {
            System.out.println("Vehículo registrado con éxito.");
        }
    }

    private static DatosBase leerDatosBase() {
        DatosBase d = new DatosBase();
        d.placa = leerPlacaUnica();
        System.out.print("Marca: ");
        d.marca = scanner.nextLine();
        System.out.print("Modelo: ");
        d.modelo = scanner.nextLine();
        d.anio = leerRangoInt("Año (1886 - 2100): ", 1886, 2100);
        d.precio = leerDoublePositivo("Precio ($): ");
        d.disponible = leerBoolean("¿Está disponible? (s/n): ");
        return d;
    }

    private static String leerPlacaUnica() {
        while (true) {
            System.out.print("Placa: ");
            String placa = scanner.nextLine().trim();
            if (placa.isEmpty()) {
                System.out.println("La placa no puede estar vacía.");
            } else if (registro.existePlaca(placa)) {
                System.out.println("Error: Ya existe un vehículo registrado con la placa '" + placa + "'.");
            } else {
                return placa;
            }
        }
    }

    private static int leerRangoInt(String mensaje, int min, int max) {
        while (true) {
            try {
                System.out.print(mensaje);
                int val = Integer.parseInt(scanner.nextLine());
                if (val >= min && val <= max) return val;
                System.out.println("El valor debe estar entre " + min + " y " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número entero válido.");
            }
        }
    }

    private static double leerDoublePositivo(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                double val = Double.parseDouble(scanner.nextLine());
                if (val >= 0) return val;
                System.out.println("El precio no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un valor numérico válido.");
            }
        }
    }

    private static boolean leerBoolean(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("s") || input.equals("si")) return true;
            if (input.equals("n") || input.equals("no")) return false;
            System.out.println("Ingrese 's' para Sí o 'n' para No.");
        }
    }
}