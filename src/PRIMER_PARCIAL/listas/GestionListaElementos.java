package listas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionListaElementos {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> lista = new ArrayList<>();
        int opcion = 0;

        do {
            System.out.println("\n=== MENÚ DE GESTIÓN ===");
            System.out.println("1. Insertar elementos");
            System.out.println("2. Modificar elemento");
            System.out.println("3. Mostrar lista");
            System.out.println("4. Eliminar elemento");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); 
            } else {
                System.out.println("¡Error! Debe ingresar un número.");
                scanner.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    // --- INSERTAR N ELEMENTOS ---
                    System.out.print("¿Cuántos elementos desea insertar?: ");
                    if (scanner.hasNextInt()) {
                        int n = scanner.nextInt();
                        scanner.nextLine(); 

                        if (n <= 0) {
                            System.out.println("Debe ingresar un número mayor a 0.");
                        } else {
                            for (int i = 0; i < n; i++) {
                                System.out.print("Ingrese el elemento [" + (i + 1) + " de " + n + "]: ");
                                String elemento = scanner.nextLine();
                                lista.add(elemento); 
                            }
                            System.out.println("-> ¡Se insertaron " + n + " elementos correctamente!");
                        }
                    } else {
                        System.out.println("¡Error! Ingrese un número entero válido.");
                        scanner.nextLine();
                    }
                    break;

                case 2:
                    // --- MODIFICAR ---
                    if (lista.isEmpty()) {
                        System.out.println("La lista está vacía.");
                    } else {
                        mostrarLista(lista);
                        System.out.print("Ingrese el número del elemento a modificar: ");
                        int indiceMod = pedirIndice(scanner, lista.size());
                        
                        if (indiceMod != -1) {
                            System.out.print("Ingrese el nuevo valor: ");
                            String nuevoValor = scanner.nextLine();
                            lista.set(indiceMod, nuevoValor);
                            System.out.println("-> Elemento modificado con éxito.");
                        }
                    }
                    break;

                case 3:
                    // --- MOSTRAR ---
                    mostrarLista(lista);
                    break;

                case 4:
                    // --- ELIMINAR ---
                    if (lista.isEmpty()) {
                        System.out.println("La lista está vacía.");
                    } else {
                        mostrarLista(lista);
                        System.out.print("Ingrese el número del elemento a eliminar: ");
                        int indiceElim = pedirIndice(scanner, lista.size());
                        
                        if (indiceElim != -1) {
                            String eliminado = lista.remove(indiceElim);
                            System.out.println("-> '" + eliminado + "' ha sido eliminado.");
                        }
                    }
                    break;

                case 5:
                    System.out.println("¡Programa finalizado!");
                    break;

                default:
                    System.out.println("Opción no válida. Intente del 1 al 5.");
            }

        } while (opcion != 5);

        scanner.close();
    }

    private static void mostrarLista(List<String> lista) {
        if (lista.isEmpty()) {
            System.out.println("La lista está vacía.");
        } else {
            System.out.println("\n--- ELEMENTOS EN LA LISTA ---");
            for (int i = 0; i < lista.size(); i++) {
                System.out.println((i + 1) + ". " + lista.get(i));
            }
        }
    }

    private static int pedirIndice(Scanner scanner, int tamanoLista) {
        if (scanner.hasNextInt()) {
            int numero = scanner.nextInt();
            scanner.nextLine();
            int indiceReal = numero - 1;

            if (indiceReal >= 0 && indiceReal < tamanoLista) {
                return indiceReal;
            } else {
                System.out.println("¡Error! El número ingresado no existe en la lista.");
                return -1;
            }
        } else {
            System.out.println("¡Error! Debe ingresar un número entero.");
            scanner.nextLine();
            return -1;
        }
    }
}