package registrovehicular;

/**
 * CONCEPTO: Herencia (Motocicleta extends Vehiculo)
 */
public class Motocicleta extends Vehiculo {
    private int cilindrada;
    private boolean tieneMaletero;

    public Motocicleta(String placa, String marca, String modelo, int anio, double precio, boolean disponible, int cilindrada, boolean tieneMaletero) {
        super(placa, marca, modelo, anio, precio, disponible);
        this.cilindrada = cilindrada;
        this.tieneMaletero = tieneMaletero;
    }

    // CONCEPTO: Polimorfismo (@Override)
    @Override
    public void mostrarInformacion() {
        System.out.print("[MOTOCICLETA] ");
        mostrarDatosComunes();
        System.out.println("            └─ Cilindrada: " + cilindrada + " cc | Maletero: " + (tieneMaletero ? "Sí" : "No"));
    }
}