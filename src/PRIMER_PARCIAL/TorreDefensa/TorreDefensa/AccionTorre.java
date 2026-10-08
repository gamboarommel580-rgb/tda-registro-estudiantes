/**
 * Clase AccionTorre: representa una acción del jugador (colocar o mejorar
 * una torre) que se guarda en la Pila de historial para poder
 * deshacerla o rehacerla más tarde.
 */
public class AccionTorre {

    public static final int COLOCAR = 1;
    public static final int MEJORA = 2;

    private int tipo;
    private Torre torre;
    private int costoOro;      // oro que costó la acción (para reembolsar/recobrar)

    // Los siguientes campos solo se usan cuando tipo == MEJORA
    private int nivelAnterior;
    private int danoAnterior;
    private int nivelNuevo;
    private int danoNuevo;

    // Constructor para acciones de tipo COLOCAR
    public AccionTorre(int tipo, Torre torre, int costoOro) {
        this.tipo = tipo;
        this.torre = torre;
        this.costoOro = costoOro;
    }

    // Constructor para acciones de tipo MEJORA
    public AccionTorre(int tipo, Torre torre, int costoOro,
                        int nivelAnterior, int danoAnterior,
                        int nivelNuevo, int danoNuevo) {
        this.tipo = tipo;
        this.torre = torre;
        this.costoOro = costoOro;
        this.nivelAnterior = nivelAnterior;
        this.danoAnterior = danoAnterior;
        this.nivelNuevo = nivelNuevo;
        this.danoNuevo = danoNuevo;
    }

    public int getTipo() {
        return tipo;
    }

    public Torre getTorre() {
        return torre;
    }

    public int getCostoOro() {
        return costoOro;
    }

    public int getNivelAnterior() {
        return nivelAnterior;
    }

    public int getDanoAnterior() {
        return danoAnterior;
    }

    public int getNivelNuevo() {
        return nivelNuevo;
    }

    public int getDanoNuevo() {
        return danoNuevo;
    }
}
