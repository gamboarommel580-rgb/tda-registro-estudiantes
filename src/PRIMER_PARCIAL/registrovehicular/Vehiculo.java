package registrovehicular;

/**
 * CONCEPTO: Clase Abstracta y Encapsulamiento
 */
public abstract class Vehiculo {
    protected String placa;
    protected String marca;
    protected String modelo;
    protected int anio;
    protected double precio;
    protected boolean disponible;

    public Vehiculo(String placa, String marca, String modelo, int anio, double precio, boolean disponible) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
        this.disponible = disponible;
    }

    public String getPlaca() {
        return placa;
    }

    // DRY: Las subclases reutilizan la impresion de atributos comunes
    protected void mostrarDatosComunes() {
        System.out.printf("Placa: %-8s | Marca: %-10s | Modelo: %-10s | Año: %d | Precio: $%.2f | Disponible: %s%n",
                placa, marca, modelo, anio, precio, (disponible ? "Sí" : "No"));
    }

    // CONCEPTO: Polimorfismo (metodo abstracto)
    public abstract void mostrarInformacion();
}