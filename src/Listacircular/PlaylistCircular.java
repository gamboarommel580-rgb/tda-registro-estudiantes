package Listacircular;

public class PlaylistCircular {

    private final ListaCircular<Cancion> lista;
    private int indiceActual;

    public PlaylistCircular() {
        lista = new ListaCircular<>();
        indiceActual = 0;
    }

    public void agregarAlInicio(Cancion cancion) {
        lista.insertarAlInicio(cancion);
    }

    public void agregarAlFinal(Cancion cancion) {
        lista.insertarAlFinal(cancion);
    }

    public void mostrarPlaylist() {
        System.out.println("  Playlist (" + lista.contarElementos() + " canciones):");
        if (lista.estaVacia()) { System.out.println("  [Vacia]"); return; }
        Nodo<Cancion> nodo = lista.getPrimero();
        int n = lista.contarElementos();
        for (int i = 0; i < n; i++) {
            String marca = (i == indiceActual) ? " << reproduciendo" : "";
            System.out.printf("  %d. %s%s%n", i + 1, nodo.dato, marca);
            nodo = nodo.siguiente;
        }
    }

    public void reproducirSiguiente() {
        if (lista.estaVacia()) { System.out.println("  Playlist vacia."); return; }
        indiceActual = (indiceActual + 1) % lista.contarElementos();
        Nodo<Cancion> nodo = lista.getPrimero();
        for (int i = 0; i < indiceActual; i++) nodo = nodo.siguiente;
        System.out.println("  >> Reproduciendo: " + nodo.dato);
    }

    public boolean eliminarPorNombre(String nombre) {
        if (lista.estaVacia()) return false;
        Nodo<Cancion> nodo = lista.getPrimero();
        int n = lista.contarElementos();
        for (int i = 0; i < n; i++) {
            if (nodo.dato.getNombre().equalsIgnoreCase(nombre)) {
                lista.eliminarPorValor(nodo.dato);
                if (!lista.estaVacia()) {
                    if (i < indiceActual) indiceActual--;
                    else if (i == indiceActual) indiceActual = indiceActual % lista.contarElementos();
                } else {
                    indiceActual = 0;
                }
                return true;
            }
            nodo = nodo.siguiente;
        }
        return false;
    }
}