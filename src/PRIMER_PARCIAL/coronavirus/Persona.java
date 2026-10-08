package coronavirus;

/**
 * TDA Persona.
 *
 * Estado abstracto:
 * - nombre
 * - infectada
 * - manosContaminadas
 *
 * La infeccion y la contaminacion de las manos son estados diferentes.
 */
public class Persona {
    private final String nombre;
    private boolean infectada;
    private boolean manosContaminadas;

    public Persona(String nombre, boolean infectada) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }

        this.nombre = nombre;
        this.infectada = infectada;
        this.manosContaminadas = false;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean estaInfectada() {
        return infectada;
    }

    public boolean tieneManosContaminadas() {
        return manosContaminadas;
    }

    /* Operaciones internas: las controla el TDA CadenaTransmision. */
    void contaminarManos() {
        manosContaminadas = true;
    }

    void lavarManos() {
        manosContaminadas = false;
    }

    void infectar() {
        infectada = true;
    }

    public void mostrarEstado() {
        System.out.printf(
                "Persona: %-8s | Infectada: %-5s | Manos contaminadas: %-5s%n",
                nombre,
                convertirSiNo(infectada),
                convertirSiNo(manosContaminadas));
    }

    private String convertirSiNo(boolean valor) {
        return valor ? "SI" : "NO";
    }
}