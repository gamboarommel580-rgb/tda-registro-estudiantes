package registrovehicular;

/**
 * CONCEPTO: Herencia (Automovil extends Vehiculo)
 */
public class Automovil extends Vehiculo {
    private int numeroPuertas;
    private boolean electrico;

    public Automovil(String placa, String marca, String modelo, int anio, double precio, boolean disponible, int numeroPuertas, boolean electrico) {
        super(placa, marca, modelo, anio, precio, disponible);
        this.numeroPuertas = numeroPuertas;
        this.electrico = electrico;
    }

    // CONCEPTO: Polimorfismo (@Override)
    @Override
    public void mostrarInformacion() {
        System.out.print("[AUTOMÓVIL]  ");
        mostrarDatosComunes();
        System.out.println("            └─ Puertas: " + numeroPuertas + " | Eléctrico: " + (electrico ? "Sí" : "No"));
    }
}