package Listacircular;

public class PlaylistCircular {

    private final ListaCircular<Cancion> lista;
    private Nodo<Cancion>                actual;

    public PlaylistCircular() {
        lista  = new ListaCircular<>();
        actual = null;
    }

    public void agregarAlInicio(Cancion cancion) {
        lista.insertarAlInicio(cancion);
        if (actual == null) actual = lista.getPrimero();
    }

    public void agregarAlFinal(Cancion cancion) {
        lista.insertarAlFinal(cancion);
        if (actual == null) actual = lista.getPrimero();
    }

    public void mostrarPlaylist() {
        System.out.println("  Playlist (" + lista.contarElementos() + " canciones):");
        if (lista.estaVacia()) { System.out.println("  [Vacia]"); return; }
        Nodo<Cancion> nodo = lista.getPrimero();
        int n = lista.contarElementos();
        for (int i = 0; i < n; i++) {
            String marca = (nodo == actual) ? " << reproduciendo" : "";
            System.out.printf("  %d. %s%s%n", i + 1, nodo.dato, marca);
            nodo = nodo.siguiente;
        }
    }

    public void reproducirSiguiente() {
        if (lista.estaVacia()) { System.out.println("  Playlist vacia."); return; }
        actual = actual.siguiente;
        System.out.println("  >> Reproduciendo: " + actual.dato);
    }

    public boolean eliminarPorNombre(String nombre) {
        if (lista.estaVacia()) return false;
        Nodo<Cancion> nodo = lista.getPrimero();
        int n = lista.contarElementos();
        for (int i = 0; i < n; i++) {
            if (nodo.dato.getNombre().equalsIgnoreCase(nombre)) {
                Nodo<Cancion> siguiente = nodo.siguiente;
                boolean eraActual = (nodo == actual);
                lista.eliminarPorValor(nodo.dato);
                if (eraActual) actual = lista.estaVacia() ? null : siguiente;
                return true;
            }
            nodo = nodo.siguiente;
        }
        return false;
    }
}