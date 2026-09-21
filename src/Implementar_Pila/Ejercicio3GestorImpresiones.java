package Implementar_Pila;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Ejercicio3GestorImpresiones {

    public static void main(String[] args) {
        Deque<String> pendientes = new ArrayDeque<>();
        Deque<String> historial = new ArrayDeque<>();
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        System.out.println("=== SISTEMA GESTOR DE IMPRESIONES ===");

        do {
            System.out.println("\n--- MENÚ DE OPCIONES ---");
            System.out.println("1. Registrar documento (offerLast)");
            System.out.println("2. Imprimir siguiente documento (pollFirst -> push)");
            System.out.println("3. Recuperar última impresión (pop -> addFirst)");
            System.out.println("4. Ver documentos pendientes y historial");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); 
            } else {
                System.out.println("Por favor, ingrese un número válido.");
                scanner.nextLine(); 
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nombre del documento (ej. Reporte.pdf): ");
                    String nombreDoc = scanner.nextLine().trim();
                    if (!nombreDoc.isEmpty()) {
                        pendientes.offerLast(nombreDoc);
                        System.out.println("-> Documento registrado al final de la cola: " + nombreDoc);
                    } else {
                        System.out.println("El nombre del documento no puede estar vacío.");
                    }
                    break;

                case 2:
                    if (!pendientes.isEmpty()) {
                        String impreso = pendientes.pollFirst();
                        historial.push(impreso);
                        System.out.println("-> Impreso con éxito: " + impreso);
                    } else {
                        System.out.println("-> No hay documentos pendientes para imprimir.");
                    }
                    break;

                case 3:
                    if (!historial.isEmpty()) {
                        String recuperado = historial.pop();
                        pendientes.addFirst(recuperado);
                        System.out.println("-> Documento recuperado y enviado al frente de la cola: " + recuperado);
                    } else {
                        System.out.println("-> El historial está vacío. No hay impresiones para recuperar.");
                    }
                    break;

                case 4:
                    System.out.println("\n-- ESTADO ACTUAL --");
                    System.out.println("Pendientes (Cola): " + pendientes);
                    System.out.println("Historial (Pila): " + historial);
                    break;

                case 5:
                    System.out.println("Saliendo del gestor de impresiones...");
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }

        } while (opcion != 5);

        scanner.close();
    }
}