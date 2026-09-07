package Listacircular;

import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            sep("MENU PRINCIPAL - LISTA CIRCULAR");
            System.out.println("  1. Lista Circular Basica");
            System.out.println("  2. Insercion y Eliminacion Controlada");
            System.out.println("  3. Simulacion Round-Robin");
            System.out.println("  4. Problema de Josephus");
            System.out.println("  5. Playlist Circular");
            System.out.println("  0. Salir");
            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> demoListaBasica();
                case 2 -> demoInsercionEliminacion();
                case 3 -> demoRoundRobin();
                case 4 -> demoJosephus();
                case 5 -> demoPlaylist();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);

        sc.close();
    }

    private static void demoListaBasica() {
        sep("1. LISTA CIRCULAR BASICA");

        ListaCircular<String> lista = new ListaCircular<>();
        int opcion;

        do {
            System.out.println("\n  Lista actual:");
            lista.mostrarTodos();
            System.out.println("  Elementos: " + lista.contarElementos()
                    + " | Vacia: " + lista.estaVacia());
            System.out.println();
            System.out.println("  1. Insertar al inicio");
            System.out.println("  2. Insertar al final");
            System.out.println("  0. Volver");
            opcion = leerEntero("  Opcion: ");

            switch (opcion) {
                case 1 -> {
                    String dato = leerString("  Dato a insertar al inicio: ");
                    lista.insertarAlInicio(dato);
                    System.out.println("  Lista despues de insertar al inicio:");
                    lista.mostrarTodos();
                }
                case 2 -> {
                    String dato = leerString("  Dato a insertar al final: ");
                    lista.insertarAlFinal(dato);
                    System.out.println("  Lista despues de insertar al final:");
                    lista.mostrarTodos();
                }
                case 0 -> {}
                default -> System.out.println("  Opcion invalida.");
            }
        } while (opcion != 0);

        System.out.println();
        System.out.println("  Por que el ultimo nodo apunta al primero:");
        System.out.println("  Ejemplo: A -> B -> C -> (regresa a A)");
        System.out.println("  El nodo C no apunta a null, sino de vuelta a A.");
        System.out.println("  Esto permite recorrer la lista de forma ciclica");
        System.out.println("  sin necesidad de reiniciar manualmente el recorrido.");
        System.out.println();
        System.out.println("  Casos especiales:");
        System.out.println("  Lista vacia  : ultimo = null");
        System.out.println("  Un nodo      : nodo.siguiente = nodo (apunta a si mismo)");
        System.out.println("  Varios nodos : ultimo.siguiente = primero");
    }

    private static void demoInsercionEliminacion() {
        sep("2. INSERCION Y ELIMINACION CONTROLADA");

        ListaCircular<String> lista = new ListaCircular<>();
        int opcion;

        do {
            System.out.println("\n  Lista antes de la operacion:");
            lista.mostrarTodos();
            System.out.println("  Elementos: " + lista.contarElementos());
            System.out.println();
            System.out.println("  1. Insertar en posicion especifica");
            System.out.println("  2. Eliminar por posicion");
            System.out.println("  3. Eliminar por valor");
            System.out.println("  0. Volver");
            opcion = leerEntero("  Opcion: ");

            switch (opcion) {
                case 1 -> {
                    String dato = leerString("  Dato a insertar: ");
                    int pos = leerEntero("  Posicion (0 al " + lista.contarElementos() + "): ");
                    try {
                        lista.insertarEnPosicion(dato, pos);
                        System.out.println("  Lista despues de insertar en posicion " + pos + ":");
                        lista.mostrarTodos();
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("  Error: " + e.getMessage());
                    }
                }
                case 2 -> {
                    if (lista.estaVacia()) {
                        System.out.println("  La lista esta vacia. No se puede eliminar.");
                        break;
                    }
                    int pos = leerEntero("  Posicion a eliminar (0 al " + (lista.contarElementos() - 1) + "): ");
                    if (lista.eliminarPorPosicion(pos)) {
                        System.out.println("  Lista despues de eliminar posicion " + pos + ":");
                        lista.mostrarTodos();
                    } else {
                        System.out.println("  Posicion invalida.");
                    }
                }
                case 3 -> {
                    if (lista.estaVacia()) {
                        System.out.println("  La lista esta vacia. No se puede eliminar.");
                        break;
                    }
                    String valor = leerString("  Valor a eliminar: ");
                    if (lista.eliminarPorValor(valor)) {
                        System.out.println("  Lista despues de eliminar '" + valor + "':");
                        lista.mostrarTodos();
                    } else {
                        System.out.println("  Valor no encontrado.");
                    }
                }
                case 0 -> {}
                default -> System.out.println("  Opcion invalida.");
            }
        } while (opcion != 0);

        System.out.println();
        System.out.println("  Analisis de casos:");
        System.out.println("  Lista vacia      : eliminar retorna false.");
        System.out.println("                     Insertar en pos > 0 lanza excepcion.");
        System.out.println("  Un solo nodo     : al eliminar, ultimo pasa a null");
        System.out.println("                     y la lista queda vacia.");
        System.out.println("  Varios nodos     : se recorre hasta la posicion,");
        System.out.println("                     se reenlaza y el circulo se mantiene.");
    }

    private static void demoRoundRobin() {
        sep("3. SIMULACION ROUND-ROBIN");

        int cantidad = leerEnteroMin("Cuantos procesos desea ingresar: ", 1);
        Proceso[] procesos = new Proceso[cantidad];

        for (int i = 0; i < cantidad; i++) {
            System.out.println("\n  Proceso " + (i + 1) + ":");
            String nombre = leerString("    Nombre: ");
            int    tiempo = leerEnteroMin("    Tiempo de ejecucion (unidades): ", 1);
            procesos[i]   = new Proceso(nombre, tiempo);
        }

        System.out.println();
        new SimuladorRoundRobin().simular(procesos);
    }

    private static void demoJosephus() {
        sep("4. PROBLEMA DE JOSEPHUS");

        Josephus josephus = new Josephus();

        System.out.println("  Caso 1:");
        josephus.resolver(5, 2);

        System.out.println("  Caso 2:");
        josephus.resolver(7, 3);

        System.out.println("  Por que la lista circular es adecuada para Josephus:");
        System.out.println("  Las personas forman un circulo y la cuenta continua");
        System.out.println("  desde donde termino la eliminacion anterior.");
        System.out.println("  La lista circular replica exactamente esto:");
        System.out.println("  al llegar al ultimo nodo, el siguiente es automaticamente");
        System.out.println("  el primero, sin reiniciar ni recalcular indices.");
    }

    private static void demoPlaylist() {
        sep("5. PLAYLIST CIRCULAR");

        PlaylistCircular playlist = new PlaylistCircular();
        int opcion;

        do {
            System.out.println("\n  Playlist actual:");
            playlist.mostrarPlaylist();
            System.out.println();
            System.out.println("  1. Agregar cancion al inicio");
            System.out.println("  2. Agregar cancion al final");
            System.out.println("  3. Reproducir siguiente");
            System.out.println("  4. Eliminar cancion por nombre");
            System.out.println("  0. Volver");
            opcion = leerEntero("  Opcion: ");

            switch (opcion) {
                case 1 -> {
                    String nombre  = leerString("  Nombre de la cancion: ");
                    String artista = leerString("  Artista: ");
                    playlist.agregarAlInicio(new Cancion(nombre, artista));
                    System.out.println("  Cancion agregada al inicio.");
                }
                case 2 -> {
                    String nombre  = leerString("  Nombre de la cancion: ");
                    String artista = leerString("  Artista: ");
                    playlist.agregarAlFinal(new Cancion(nombre, artista));
                    System.out.println("  Cancion agregada al final.");
                }
                case 3 -> playlist.reproducirSiguiente();
                case 4 -> {
                    String nombre = leerString("  Nombre de la cancion a eliminar: ");
                    if (playlist.eliminarPorNombre(nombre)) {
                        System.out.println("  Cancion eliminada.");
                    } else {
                        System.out.println("  Cancion no encontrada.");
                    }
                }
                case 0 -> {}
                default -> System.out.println("  Opcion invalida.");
            }
        } while (opcion != 0);

        System.out.println();
        System.out.println("  Ventaja de lista circular frente a lista lineal:");
        System.out.println("  Lista lineal   : al llegar al final hay que reiniciar");
        System.out.println("                   manualmente al inicio con logica extra.");
        System.out.println("  Lista circular : el ultimo nodo apunta al primero.");
        System.out.println("                   La reproduccion continua es automatica,");
        System.out.println("                   sin condiciones adicionales.");
    }

    private static void sep(String titulo) {
        System.out.println("\n" + "=".repeat(55));
        System.out.println("  " + titulo);
        System.out.println("=".repeat(55));
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Ingrese un numero entero valido.");
            }
        }
    }

    private static int leerEnteroMin(String mensaje, int min) {
        while (true) {
            int val = leerEntero(mensaje);
            if (val >= min) return val;
            System.out.println("  El valor debe ser al menos " + min + ".");
        }
    }

    private static String leerString(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String txt = sc.nextLine().trim();
            if (!txt.isEmpty()) return txt;
            System.out.println("  El campo no puede estar vacio.");
        }
    }
}