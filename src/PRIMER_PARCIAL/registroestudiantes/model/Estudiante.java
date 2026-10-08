package registroestudiantes.model;

public class Estudiante {
    private final long id;
    private String nombre;
    private int edad;
    private double promedio;

    public Estudiante(long id, String nombre, int edad, double promedio) {
        validarId(id);
        validarNombre(nombre);
        validarEdad(edad);
        validarPromedio(promedio);
        
        this.id = id;
        this.nombre = nombre.trim();
        this.edad = edad;
        this.promedio = promedio;
    }

    public long getId() { return id; }
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public double getPromedio() { return promedio; }

    public void setNombre(String nombre) {
        validarNombre(nombre);
        this.nombre = nombre.trim();
    }

    public void setEdad(int edad) {
        validarEdad(edad);
        this.edad = edad;
    }

    public void setPromedio(double promedio) {
        validarPromedio(promedio);
        this.promedio = promedio;
    }

    public void actualizarDatos(String nombre, int edad, double promedio) {
        validarNombre(nombre);
        validarEdad(edad);
        validarPromedio(promedio);
        this.nombre = nombre.trim();
        this.edad = edad;
        this.promedio = promedio;
    }

    private static void validarId(long id) {
        if (id < 10000000L || id > 9999999999L) {
            throw new IllegalArgumentException("El ID debe tener entre 8 y 10 digitos.");
        }
    }

    private static void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
        if (nombre.trim().length() < 2) {
            throw new IllegalArgumentException("El nombre debe tener al menos 2 caracteres.");
        }
    }

    private static void validarEdad(int edad) {
        if (edad < 15 || edad > 100) {
            throw new IllegalArgumentException("La edad debe estar entre 15 y 100 años.");
        }
    }

    private static void validarPromedio(double promedio) {
        if (promedio < 0.0 || promedio > 10.0) {
            throw new IllegalArgumentException("El promedio debe estar entre 0.0 y 10.0.");
        }
    }

    @Override
    public String toString() {
        return String.format("ID: %-10d | Nombre: %-25s | Edad: %3d | Promedio: %5.2f",
                id, nombre, edad, promedio);
    }
}