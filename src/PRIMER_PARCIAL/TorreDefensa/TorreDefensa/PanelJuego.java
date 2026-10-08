import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Clase PanelJuego: es el "tablero" donde ocurre el juego.
 * Aquí viven la ruta, las torres, la cola circular de enemigos activos,
 * la cola de oleadas y las pilas de deshacer/rehacer.
 */
public class PanelJuego extends JPanel {

    // ---- Configuración del mapa ----
    private static final int COLUMNAS = 16;
    private static final int FILAS = 10;
    private static final int TAM_CELDA = 45;
    private static final int QUANTUM_MS = 600; // duración de cada quantum de tiempo
    private static final int QUANTUMS_ENTRE_SPAWN = 2; // cada cuántos quantums aparece un enemigo nuevo

    // Ruta predefinida (columna, fila) desde la entrada hasta la fuente de energía
    private final int[][] ruta = {
        {0, 4}, {1, 4}, {2, 4}, {3, 4}, {4, 4}, {4, 3}, {4, 2},
        {5, 2}, {6, 2}, {7, 2}, {8, 2}, {8, 3}, {8, 4}, {8, 5},
        {8, 6}, {8, 7}, {9, 7}, {10, 7}, {11, 7}, {12, 7}, {12, 6},
        {12, 5}, {12, 4}, {12, 3}, {12, 2}, {13, 2}, {14, 2}, {15, 2}
    };
    private boolean[][] esRuta;

    // ---- Estado del jugador ----
    private int vidaJugador = 20;
    private int oro = 150;
    private int puntuacion = 0;
    private int numeroOleadaActual = 0;
    private boolean juegoTerminado = false;

    // ---- Estructuras de datos principales ----
    private ArrayList<Torre> listaTorres;       // lista secuencial de torres colocadas
    private ColaCircular colaEnemigosActivos;   // cola circular: enemigos en la ruta
    private ColaOleadas colaOleadas;            // cola: orden de las oleadas
    private PilaAcciones pilaDeshacer;          // pila: historial para deshacer
    private PilaAcciones pilaRehacer;           // pila: historial para rehacer

    // ---- Generación de oleada actual ----
    private Oleada oleadaActual;
    private int enemigosGeneradosDeOleada;
    private int quantumsDesdeUltimoSpawn;

    // ---- Selección del jugador en la interfaz ----
    private int tipoTorreSeleccionada = Torre.TIPO_BASICA;
    private boolean modoMejora = false;

    private Timer temporizador;

    public PanelJuego() {
        setPreferredSize(new java.awt.Dimension(COLUMNAS * TAM_CELDA, FILAS * TAM_CELDA));
        setBackground(new Color(222, 214, 190));

        listaTorres = new ArrayList<Torre>();
        colaEnemigosActivos = new ColaCircular(100);
        colaOleadas = new ColaOleadas();
        pilaDeshacer = new PilaAcciones();
        pilaRehacer = new PilaAcciones();

        marcarCeldasDeRuta();
        generarOleadasIniciales();

        // Listener del mouse: colocar torres o mejorarlas con un clic.
        // Se usa mousePressed en lugar de mouseClicked porque mouseClicked
        // solo se dispara si el mouse no se mueve NADA entre presionar y
        // soltar; con mousePressed el clic responde siempre.
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                manejarClic(e.getX(), e.getY());
            }
        });

        // El temporizador dispara un "quantum de tiempo" cada QUANTUM_MS
        temporizador = new Timer(QUANTUM_MS, e -> actualizarJuego());
        temporizador.start();
    }

    // Marca en una matriz booleana qué celdas pertenecen a la ruta
    private void marcarCeldasDeRuta() {
        esRuta = new boolean[COLUMNAS][FILAS];
        for (int i = 0; i < ruta.length; i++) {
            int col = ruta[i][0];
            int fila = ruta[i][1];
            esRuta[col][fila] = true;
        }
    }

    // Genera de antemano varias oleadas con dificultad creciente y las encola
    private void generarOleadasIniciales() {
        int totalOleadas = 8;
        for (int i = 1; i <= totalOleadas; i++) {
            int cantidadEnemigos = 5 + (i * 2);
            int vidaEnemigos = 30 + (i * 15);
            int oroPorEnemigo = 10 + i;
            colaOleadas.encolar(new Oleada(i, cantidadEnemigos, vidaEnemigos, oroPorEnemigo));
        }
    }

    // ---------------------------------------------------------------
    // Manejo de clics del jugador (colocar o mejorar torres)
    // ---------------------------------------------------------------
    private void manejarClic(int x, int y) {
        if (juegoTerminado) {
            return;
        }
        int columna = x / TAM_CELDA;
        int fila = y / TAM_CELDA;
        if (columna < 0 || columna >= COLUMNAS || fila < 0 || fila >= FILAS) {
            return;
        }

        if (modoMejora) {
            Torre torre = buscarTorreEn(columna, fila);
            if (torre != null) {
                mejorarTorre(torre);
            }
        } else {
            if (esRuta[columna][fila]) {
                return; // no se puede construir sobre la ruta
            }
            if (buscarTorreEn(columna, fila) != null) {
                return; // ya hay una torre en esa celda
            }
            colocarTorre(columna, fila);
        }
        repaint();
    }

    private Torre buscarTorreEn(int columna, int fila) {
        for (int i = 0; i < listaTorres.size(); i++) {
            Torre t = listaTorres.get(i);
            if (t.getColumna() == columna && t.getFila() == fila) {
                return t;
            }
        }
        return null;
    }

    private void colocarTorre(int columna, int fila) {
        Torre nueva = new Torre(tipoTorreSeleccionada, columna, fila);
        if (oro < nueva.getCostoBase()) {
            JOptionPane.showMessageDialog(this, "No tienes suficiente oro para esta torre.");
            return;
        }
        oro = oro - nueva.getCostoBase();
        listaTorres.add(nueva);

        // Guardamos la acción en la pila de deshacer y limpiamos la de rehacer
        pilaDeshacer.apilar(new AccionTorre(AccionTorre.COLOCAR, nueva, nueva.getCostoBase()));
        pilaRehacer.vaciar();
    }

    private void mejorarTorre(Torre torre) {
        int costoMejora = torre.getCostoMejora();
        if (oro < costoMejora) {
            JOptionPane.showMessageDialog(this, "No tienes suficiente oro para mejorar esta torre.");
            return;
        }
        int nivelAnterior = torre.getNivel();
        int danoAnterior = torre.getDano();

        oro = oro - costoMejora;
        torre.mejorar();

        AccionTorre accion = new AccionTorre(AccionTorre.MEJORA, torre, costoMejora,
                nivelAnterior, danoAnterior, torre.getNivel(), torre.getDano());
        pilaDeshacer.apilar(accion);
        pilaRehacer.vaciar();
    }

    // ---------------------------------------------------------------
    // Deshacer / Rehacer
    // ---------------------------------------------------------------
    public void deshacer() {
        if (pilaDeshacer.estaVacia()) {
            return;
        }
        AccionTorre accion = pilaDeshacer.desapilar();

        if (accion.getTipo() == AccionTorre.COLOCAR) {
            listaTorres.remove(accion.getTorre());
            oro = oro + accion.getCostoOro();
        } else { // MEJORA
            accion.getTorre().restaurarNivel(accion.getNivelAnterior(), accion.getDanoAnterior());
            oro = oro + accion.getCostoOro();
        }

        pilaRehacer.apilar(accion);
        repaint();
    }

    public void rehacer() {
        if (pilaRehacer.estaVacia()) {
            return;
        }
        AccionTorre accion = pilaRehacer.desapilar();

        if (accion.getTipo() == AccionTorre.COLOCAR) {
            if (oro >= accion.getCostoOro()) {
                oro = oro - accion.getCostoOro();
                listaTorres.add(accion.getTorre());
            }
        } else { // MEJORA
            if (oro >= accion.getCostoOro()) {
                oro = oro - accion.getCostoOro();
                accion.getTorre().restaurarNivel(accion.getNivelNuevo(), accion.getDanoNuevo());
            }
        }

        pilaDeshacer.apilar(accion);
        repaint();
    }

    // ---------------------------------------------------------------
    // Bucle principal del juego: se ejecuta una vez por cada quantum
    // ---------------------------------------------------------------
    private void actualizarJuego() {
        if (juegoTerminado) {
            return;
        }

        generarEnemigosDeOleadaSiCorresponde();
        procesarColaCircularDeEnemigos();
        verificarFinDeJuego();

        repaint();
    }

    // Si la oleada actual ya generó todos sus enemigos, toma la siguiente de la cola.
    // Luego, cada cierto número de quantums, agrega un nuevo Cozy a la cola circular.
    private void generarEnemigosDeOleadaSiCorresponde() {
        boolean sinOleadaActiva = (oleadaActual == null)
                || (enemigosGeneradosDeOleada >= oleadaActual.getCantidadEnemigos());

        if (sinOleadaActiva && colaEnemigosActivos.estaVacia() && !colaOleadas.estaVacia()) {
            oleadaActual = colaOleadas.desencolar();
            enemigosGeneradosDeOleada = 0;
            quantumsDesdeUltimoSpawn = 0;
            numeroOleadaActual = oleadaActual.getNumeroOleada();
        }

        if (oleadaActual == null) {
            return;
        }
        if (enemigosGeneradosDeOleada >= oleadaActual.getCantidadEnemigos()) {
            return;
        }

        quantumsDesdeUltimoSpawn = quantumsDesdeUltimoSpawn + 1;
        if (quantumsDesdeUltimoSpawn >= QUANTUMS_ENTRE_SPAWN) {
            quantumsDesdeUltimoSpawn = 0;
            Cozy nuevo = new Cozy(oleadaActual.getVidaEnemigos(), oleadaActual.getOroPorEnemigo(), ruta);
            colaEnemigosActivos.encolar(nuevo);
            enemigosGeneradosDeOleada = enemigosGeneradosDeOleada + 1;
        }
    }

    /**
     * Da una vuelta completa a la cola circular de enemigos activos:
     * a cada uno le aplica el daño de las torres en rango, lo mueve un
     * paso en la ruta y decide si vuelve a la cola, si murió, o si
     * llegó al final (el jugador pierde vida).
     */
    private void procesarColaCircularDeEnemigos() {
        int cantidadEsteQuantum = colaEnemigosActivos.getCantidad();

        for (int i = 0; i < cantidadEsteQuantum; i++) {
            Cozy actual = colaEnemigosActivos.desencolar();

            int danoTotal = calcularDanoDeTorresEnRango(actual);
            actual.recibirDano(danoTotal);

            if (!actual.estaVivo()) {
                oro = oro + actual.getOroAlMorir();
                puntuacion = puntuacion + actual.getOroAlMorir();
                continue; // el enemigo no vuelve a la cola: fue destruido
            }

            boolean llegoAlFinal = actual.avanzar(ruta);
            if (llegoAlFinal) {
                vidaJugador = vidaJugador - 1;
                continue; // tampoco vuelve a la cola
            }

            colaEnemigosActivos.encolar(actual); // sigue vivo y en camino: vuelve a la cola
        }
    }

    private int calcularDanoDeTorresEnRango(Cozy enemigo) {
        int total = 0;
        for (int i = 0; i < listaTorres.size(); i++) {
            Torre t = listaTorres.get(i);
            if (t.enRango(enemigo.getColumnaActual(), enemigo.getFilaActual())) {
                total = total + t.getDano();
            }
        }
        return total;
    }

    private void verificarFinDeJuego() {
        if (vidaJugador <= 0) {
            juegoTerminado = true;
            temporizador.stop();
            JOptionPane.showMessageDialog(this, "¡Has perdido! La fuente de energía fue destruida.\nPuntuación final: " + puntuacion);
        } else if (colaOleadas.estaVacia() && colaEnemigosActivos.estaVacia()
                && oleadaActual != null
                && enemigosGeneradosDeOleada >= oleadaActual.getCantidadEnemigos()) {
            juegoTerminado = true;
            temporizador.stop();
            JOptionPane.showMessageDialog(this, "¡Victoria! Sobreviviste a todas las oleadas.\nPuntuación final: " + puntuacion);
        }
    }

    // ---------------------------------------------------------------
    // Métodos usados por la ventana (VentanaJuego) para los controles
    // ---------------------------------------------------------------
    public void seleccionarTipoTorre(int tipo) {
        this.tipoTorreSeleccionada = tipo;
        this.modoMejora = false;
    }

    public void activarModoMejora() {
        this.modoMejora = true;
    }

    public int getVidaJugador() {
        return vidaJugador;
    }

    public int getOro() {
        return oro;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public int getNumeroOleadaActual() {
        return numeroOleadaActual;
    }

    // ---------------------------------------------------------------
    // Dibujo del tablero
    // ---------------------------------------------------------------
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        dibujarCuadricula(g2);
        dibujarRuta(g2);
        dibujarTorres(g2);
        dibujarEnemigos(g2);
    }

    private void dibujarCuadricula(Graphics2D g2) {
        g2.setColor(new Color(200, 190, 160));
        for (int c = 0; c <= COLUMNAS; c++) {
            g2.drawLine(c * TAM_CELDA, 0, c * TAM_CELDA, FILAS * TAM_CELDA);
        }
        for (int f = 0; f <= FILAS; f++) {
            g2.drawLine(0, f * TAM_CELDA, COLUMNAS * TAM_CELDA, f * TAM_CELDA);
        }
    }

    private void dibujarRuta(Graphics2D g2) {
        g2.setColor(new Color(170, 140, 100));
        for (int i = 0; i < ruta.length; i++) {
            int x = ruta[i][0] * TAM_CELDA;
            int y = ruta[i][1] * TAM_CELDA;
            g2.fillRect(x, y, TAM_CELDA, TAM_CELDA);
        }

        // Entrada de enemigos
        g2.setColor(Color.DARK_GRAY);
        g2.drawString("Entrada", ruta[0][0] * TAM_CELDA, ruta[0][1] * TAM_CELDA - 5);

        // Fuente de energía (última celda de la ruta)
        int ultimo = ruta.length - 1;
        int fx = ruta[ultimo][0] * TAM_CELDA;
        int fy = ruta[ultimo][1] * TAM_CELDA;
        g2.setColor(Color.YELLOW);
        g2.fillOval(fx + 8, fy + 8, TAM_CELDA - 16, TAM_CELDA - 16);
        g2.setColor(Color.ORANGE.darker());
        g2.drawOval(fx + 8, fy + 8, TAM_CELDA - 16, TAM_CELDA - 16);
    }

    private void dibujarTorres(Graphics2D g2) {
        for (int i = 0; i < listaTorres.size(); i++) {
            Torre t = listaTorres.get(i);
            int x = t.getColumna() * TAM_CELDA;
            int y = t.getFila() * TAM_CELDA;

            // Círculo de rango, tenue
            g2.setColor(new Color(t.getColor().getRed(), t.getColor().getGreen(), t.getColor().getBlue(), 40));
            int radioPx = t.getRango() * TAM_CELDA;
            int cx = x + TAM_CELDA / 2;
            int cy = y + TAM_CELDA / 2;
            g2.fillOval(cx - radioPx, cy - radioPx, radioPx * 2, radioPx * 2);

            // Cuerpo de la torre
            g2.setColor(t.getColor());
            g2.fillRect(x + 6, y + 6, TAM_CELDA - 12, TAM_CELDA - 12);
            g2.setColor(Color.BLACK);
            g2.drawRect(x + 6, y + 6, TAM_CELDA - 12, TAM_CELDA - 12);

            g2.setFont(new Font("Arial", Font.BOLD, 11));
            g2.drawString("N" + t.getNivel(), x + 10, y + TAM_CELDA - 10);
        }
    }

    private void dibujarEnemigos(Graphics2D g2) {
        Cozy[] enemigos = colaEnemigosActivos.obtenerTodos();
        for (int i = 0; i < enemigos.length; i++) {
            Cozy c = enemigos[i];
            int x = c.getColumnaActual() * TAM_CELDA;
            int y = c.getFilaActual() * TAM_CELDA;

            g2.setColor(c.getColor());
            g2.fillOval(x + 10, y + 10, TAM_CELDA - 20, TAM_CELDA - 20);
            g2.setColor(Color.BLACK);
            g2.drawOval(x + 10, y + 10, TAM_CELDA - 20, TAM_CELDA - 20);

            // Barra de vida
            int anchoBarra = TAM_CELDA - 14;
            double proporcion = (double) c.getPvActual() / (double) c.getPvMaximo();
            g2.setColor(Color.RED);
            g2.fillRect(x + 7, y + 4, anchoBarra, 5);
            g2.setColor(Color.GREEN);
            g2.fillRect(x + 7, y + 4, (int) (anchoBarra * proporcion), 5);
            g2.setColor(Color.BLACK);
            g2.drawRect(x + 7, y + 4, anchoBarra, 5);
        }
    }
}
