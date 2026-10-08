import java.util.ArrayList;
import java.util.List;

public class Estructuras {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     EJERCICIO 1: SELECCIÓN DE ESTRUCTURAS       ");
        System.out.println("==================================================\n");

        double[] temperaturasSemana = new double[7];
        System.out.println("1. Registrar las temperaturas de los siete días de la semana:");
        System.out.println("   -> Estática: double[7]. El número de días es fijo.");

        List<String> cursoVirtual = new ArrayList<>();
        System.out.println("\n2. Registrar estudiantes que ingresan y abandonan un curso virtual:");
        System.out.println("   -> Dinámica: La cantidad puede cambiar constantemente.");

        double[] mesesAnio = new double[12];
        System.out.println("\n3. Representar los doce meses del año:");
        System.out.println("   -> Estática: double[12]. Es un conjunto invariable de datos.");

        List<String> solicitudesSoporte = new ArrayList<>();
        System.out.println("\n4. Gestionar solicitudes que llegan continuamente a soporte técnico:");
        System.out.println("   -> Dinámica: Las solicitudes aparecen continuamente.");

        System.out.println("\n5. Guardar las calificaciones de 30 estudiantes matriculados:");
        System.out.println("   -> Estática (double[30]) si el grupo está cerrado.");
        System.out.println("   -> Dinámica si pueden existir cambios de matrícula.");
        
        System.out.println("\n==================================================");
    }
}
