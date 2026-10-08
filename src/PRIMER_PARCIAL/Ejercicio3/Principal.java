package Ejercicio3;

public class Principal {
    public static void main(String[] args) {
      System.out.println("==================================================");
        System.out.println("  EJERCICIO 3: CLASES, OBJETOS E INSTANCIACIÓN");
        System.out.println("==================================================\n");

        Estudiante estudiante1 = new Estudiante("001", "Andrea López", 8.7);
        Estudiante estudiante2 = new Estudiante("002", "Carlos Pérez", 6.4);

        System.out.println("--- Estudiante 1 ---");
        System.out.println(estudiante1.mostrarResumen());
        System.out.println("¿Aprueba?: " + estudiante1.aprueba());

        System.out.println("\n--- Estudiante 2 ---");
        System.out.println(estudiante2.mostrarResumen());
        System.out.println("¿Aprueba?: " + estudiante2.aprueba());

        System.out.println("\n==================================================");

    }
    
}
