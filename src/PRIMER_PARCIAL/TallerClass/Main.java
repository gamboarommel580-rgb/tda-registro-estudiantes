package TallerClass;

public class Main {
    public static void main(String[] args) {
        Estudiante e = new Estudiante("Carlos Perez", 3);

        System.out.println("Registro 1 (8.5): " + e.registrarCalificacion(8.5));
        System.out.println("Registro 2 (9.0): " + e.registrarCalificacion(9.0));
        System.out.println("Registro 3 (7.5): " + e.registrarCalificacion(7.5));
        System.out.println("Registro 4 (Exceso): " + e.registrarCalificacion(10.0));

        System.out.println("\nEstudiante: " + e.getNombre());
        System.out.printf("Promedio actual: %.2f\n", e.calcularPromedio());
    }
    
}
