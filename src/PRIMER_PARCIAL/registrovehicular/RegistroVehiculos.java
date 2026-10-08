package registrovehicular;

/**
 * CONCEPTO: TDA RegistroVehiculos (Estructura estatica)
 */
public class RegistroVehiculos {
    private static final int CAPACIDAD = 10;
    private final Vehiculo[] vehiculos = new Vehiculo[CAPACIDAD];
    private int cantidad = 0;

    public boolean estaLleno() {
        return cantidad >= CAPACIDAD;
    }

    public boolean existePlaca(String placa) {
        for (int i = 0; i < cantidad; i++) {
            if (vehiculos[i].getPlaca().equalsIgnoreCase(placa)) {
                return true;
            }
        }
        return false;
    }

    public boolean registrar(Vehiculo nuevoVehiculo) {
        if (nuevoVehiculo == null || estaLleno() || existePlaca(nuevoVehiculo.getPlaca())) {
            return false;
        }
        vehiculos[cantidad] = nuevoVehiculo;
        cantidad++;
        return true;
    }

    // CONCEPTO: Polimorfismo en tiempo de ejecucion
    public void mostrarTodos() {
        if (cantidad == 0) {
            System.out.println("No hay vehículos registrados.");
            return;
        }

        System.out.println("\n--- LISTADO DE VEHÍCULOS REGISTRADOS (" + cantidad + "/" + CAPACIDAD + ") ---");
        for (int i = 0; i < cantidad; i++) {
            vehiculos[i].mostrarInformacion();
        }
    }
}