package Ejercicio2;

public class Clasificacion {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   EJERCICIO 2: CLASIFICACIÓN DE VARIABLES Y SEGUIMIENTO");
        System.out.println("==================================================\n");

        int edad = 19;
        double promedio = 8.5;
        String nombre = "Carlos";
        int[] calificaciones = {8, 9, 7};
        Object estudiante = null; 
        boolean matriculado = true;

        System.out.println("--- 1. CLASIFICACIÓN DE TIPOS DE DATOS ---");
        System.out.println("• Primitivos : edad (" + edad + "), promedio (" + promedio + "), matriculado (" + matriculado + ")");
        System.out.println("• Referencias: nombre (\"" + nombre + "\"), calificaciones (arreglo), estudiante (" + estudiante + ")");

        System.out.println("\n--- 2. SEGUIMIENTO DE EJECUCIÓN ---");
        int a = 5;
        int b = a;
        b = 9;

        System.out.println("System.out.println(a) -> " + a);
        System.out.println("System.out.println(b) -> " + b);
        
        System.out.println("\nExplicación: Al ser primitivos, 'b' recibe una copia del valor de 'a'. Modificar 'b' no altera el valor de 'a'.");
        System.out.println("==================================================");
    }
}