import javax.swing.SwingUtilities;

/**
 * Clase Main: punto de entrada del programa.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaJuego ventana = new VentanaJuego();
            ventana.setVisible(true);
        });
    }
}
