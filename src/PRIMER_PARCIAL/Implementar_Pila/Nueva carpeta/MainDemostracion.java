package Implementar_Pila;

public class MainDemostracion {
    public static void main(String[] args) {
        System.out.println("=== 1. DEMOSTRACIÓN PILA MANUAL (NODOS) ===");
        PilaNodos<String> pilaManual = new PilaNodos<>();
        pilaManual.apilar("Acción 1 (Inicio)");
        pilaManual.apilar("Acción 2 (Ataque Torre)");
        pilaManual.apilar("Acción 3 (Movimiento Enemigo)");

        System.out.println("Cima actual: " + pilaManual.verCima());
        System.out.println("Desapilando: " + pilaManual.desapilar());
        System.out.println("Nueva cima: " + pilaManual.verCima());

        System.out.println("\n=== 2. DEMOSTRACIÓN PILA NATIVA (ArrayDeque) ===");
        PilaNativa<String> pilaNativa = new PilaNativa<>();
        pilaNativa.apilar("Acción 1 (Inicio)");
        pilaNativa.apilar("Acción 2 (Ataque Torre)");
        pilaNativa.apilar("Acción 3 (Movimiento Enemigo)");

        System.out.println("Cima actual: " + pilaNativa.verCima());
        System.out.println("Desapilando: " + pilaNativa.desapilar());
        System.out.println("Nueva cima: " + pilaNativa.verCima());
    }
}