package Listacircular;

public class Cancion {
    private final String nombre;
    private final String artista;

    public Cancion(String nombre, String artista) {
        this.nombre  = nombre;
        this.artista = artista;
    }

    public String getNombre()  { return nombre; }
    public String getArtista() { return artista; }

    @Override
    public String toString() {
        return "\"" + nombre + "\" - " + artista;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cancion)) return false;
        return nombre.equalsIgnoreCase(((Cancion) obj).nombre);
    }
}