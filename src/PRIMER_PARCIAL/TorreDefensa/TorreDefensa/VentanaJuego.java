import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;

/**
 * Clase VentanaJuego: arma la ventana principal (JFrame) con:
 * - el panel del juego en el centro,
 * - el panel de estadísticas arriba (vida, oro, oleada, puntuación),
 * - el panel de controles a la derecha (torres, deshacer, rehacer).
 */
public class VentanaJuego extends JFrame {

    private PanelJuego panelJuego;

    private JLabel etiquetaVida;
    private JLabel etiquetaOro;
    private JLabel etiquetaOleada;
    private JLabel etiquetaPuntuacion;

    public VentanaJuego() {
        super("Defensa de la Torre - Cozy Invasion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panelJuego = new PanelJuego();

        add(construirPanelSuperior(), BorderLayout.NORTH);
        add(panelJuego, BorderLayout.CENTER);
        add(construirPanelControles(), BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);

        // Timer independiente solo para refrescar las etiquetas de estado
        Timer temporizadorEtiquetas = new Timer(200, e -> actualizarEtiquetas());
        temporizadorEtiquetas.start();
    }

    private JPanel construirPanelSuperior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        Font fuente = new Font("Arial", Font.BOLD, 14);

        etiquetaVida = new JLabel();
        etiquetaVida.setFont(fuente);
        etiquetaOro = new JLabel();
        etiquetaOro.setFont(fuente);
        etiquetaOleada = new JLabel();
        etiquetaOleada.setFont(fuente);
        etiquetaPuntuacion = new JLabel();
        etiquetaPuntuacion.setFont(fuente);

        panel.add(etiquetaVida);
        panel.add(etiquetaOro);
        panel.add(etiquetaOleada);
        panel.add(etiquetaPuntuacion);

        actualizarEtiquetas();
        return panel;
    }

    private JPanel construirPanelControles() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 6, 6));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("Torres");
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(titulo);

        ButtonGroup grupoTorres = new ButtonGroup();

        JRadioButton radioBasica = new JRadioButton("Basica (50 oro)", true);
        JRadioButton radioAlcance = new JRadioButton("Alcance (70 oro)");
        JRadioButton radioPesada = new JRadioButton("Pesada (100 oro)");

        grupoTorres.add(radioBasica);
        grupoTorres.add(radioAlcance);
        grupoTorres.add(radioPesada);

        radioBasica.addActionListener(e -> panelJuego.seleccionarTipoTorre(Torre.TIPO_BASICA));
        radioAlcance.addActionListener(e -> panelJuego.seleccionarTipoTorre(Torre.TIPO_ALCANCE));
        radioPesada.addActionListener(e -> panelJuego.seleccionarTipoTorre(Torre.TIPO_PESADA));

        panel.add(radioBasica);
        panel.add(radioAlcance);
        panel.add(radioPesada);

        JButton botonMejorar = new JButton("Modo mejorar torre");
        botonMejorar.addActionListener(e -> panelJuego.activarModoMejora());
        panel.add(botonMejorar);

        panel.add(new JLabel(" "));

        JLabel tituloHistorial = new JLabel("Historial");
        tituloHistorial.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(tituloHistorial);

        JButton botonDeshacer = new JButton("Deshacer");
        botonDeshacer.addActionListener(e -> panelJuego.deshacer());
        panel.add(botonDeshacer);

        JButton botonRehacer = new JButton("Rehacer");
        botonRehacer.addActionListener(e -> panelJuego.rehacer());
        panel.add(botonRehacer);

        panel.add(new JLabel(" "));
        JLabel ayuda = new JLabel("<html><body style='width:150px'>"
                + "Clic en el mapa para colocar una torre. "
                + "Activa 'Modo mejorar torre' y haz clic sobre una "
                + "torre existente para mejorarla.</body></html>");
        panel.add(ayuda);

        return panel;
    }

    private void actualizarEtiquetas() {
        etiquetaVida.setText("Vida: " + panelJuego.getVidaJugador());
        etiquetaOro.setText("Oro: " + panelJuego.getOro());
        etiquetaOleada.setText("Oleada: " + panelJuego.getNumeroOleadaActual());
        etiquetaPuntuacion.setText("Puntuacion: " + panelJuego.getPuntuacion());
    }
}
