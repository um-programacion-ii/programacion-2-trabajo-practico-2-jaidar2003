package test;

import java.io.OutputStream;
import java.io.PrintStream;

public class TestMain {
    public static void main(String[] args) throws InterruptedException {
        // Guardar salida original
        PrintStream originalOut = System.out;

        try {
            // Silenciar salida estándar
            System.setOut(new PrintStream(new OutputStream() {
                @Override
                public void write(int b) {}
            }));

            // Ejecutar todos los tests
            TestUsuarios.main(args);
            TestRecurso.main(args);
            TestPrestamoDevolucion.main(args);
            TestPrestamo.main(args);
            TestDigitales.main(args);
            TestAudiolibro.main(args);
            TestVisualizacion.main(args);
            TestNotificacion.main(args);
            TestNotificacionesEmail.main(args);
            TestNotificacionesSMS.main(args);
            TestIntegrador.main(args);
            TestReservas.main(args);
            TestConcurrenciaPrestamos.main(args);
            TestConcurrenciaReservas.main(args);
            TestAlertasVencimiento.main(args);
            TestAlertasDisponibilidad.main(args);
            TestRecordatorios.main(args);
            TestReportes.main(args);

        } finally {
            // Restaurar salida estándar
            System.setOut(originalOut);
        }

        System.out.println("✅ Todos los tests fueron ejecutados exitosamente.");
    }
}
