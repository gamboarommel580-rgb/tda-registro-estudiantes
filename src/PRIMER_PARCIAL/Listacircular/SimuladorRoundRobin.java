package Listacircular;

public class SimuladorRoundRobin {

    private static final int QUANTUM = 2;

    public void simular(Proceso[] procesos) {
        ListaCircular<Proceso> cola = new ListaCircular<>();
        for (Proceso p : procesos) cola.insertarAlFinal(p);

        System.out.println("=== SIMULACION ROUND-ROBIN (quantum=" + QUANTUM + ") ===");
        System.out.print("Cola inicial: ");
        cola.mostrarTodos();
        System.out.println();

        int turno = 1;
        while (!cola.estaVacia()) {
            Nodo<Proceso> nodo    = cola.getPrimero();
            Proceso       proceso = nodo.dato;

            System.out.printf("Turno %d | Ejecutando: %-5s | tiempo antes: %d%n",
                    turno++, proceso.getNombre(), proceso.getTiempoRestante());

            proceso.ejecutar(QUANTUM);

            if (proceso.haTerminado()) {
                System.out.printf("         %-5s TERMINO. Se elimina.%n",
                        proceso.getNombre());
                cola.eliminarPorPosicion(0);
            } else {
                System.out.printf("         %-5s tiempo restante: %d. Vuelve al final.%n",
                        proceso.getNombre(), proceso.getTiempoRestante());
                cola.eliminarPorPosicion(0);
                cola.insertarAlFinal(proceso);
            }

            System.out.print("         Cola actual: ");
            cola.mostrarTodos();
            System.out.println();
        }
        System.out.println("Todos los procesos han terminado.\n");
    }
}