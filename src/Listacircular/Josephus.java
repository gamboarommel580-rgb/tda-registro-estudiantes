package Listacircular;

public class Josephus {

    public void resolver(int n, int k) {
        System.out.printf("=== JOSEPHUS: n=%d, k=%d ===%n", n, k);

        ListaCircular<Integer> circulo = new ListaCircular<>();
        for (int i = 1; i <= n; i++) circulo.insertarAlFinal(i);

        System.out.print("Circulo inicial: ");
        circulo.mostrarTodos();

        Nodo<Integer> actual = circulo.getUltimo();

        while (circulo.contarElementos() > 1) {
            for (int paso = 0; paso < k - 1; paso++)
                actual = actual.siguiente;

            Nodo<Integer> aEliminar = actual.siguiente;
            int persona = aEliminar.dato;
            System.out.printf("  Eliminada: persona %d%n", persona);

            Nodo<Integer> siguiente = aEliminar.siguiente;
            circulo.eliminarPorValor(persona);

            if (circulo.contarElementos() > 0)
                actual = (siguiente == aEliminar) ? circulo.getUltimo() : actual;
        }

        System.out.println("Superviviente: persona " + circulo.getPrimero().dato);
        System.out.println();
    }
}