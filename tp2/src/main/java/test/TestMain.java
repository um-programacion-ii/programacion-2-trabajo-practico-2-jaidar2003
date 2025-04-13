package test;

public class TestMain {
    public static void main(String[] args) {
        System.out.println("🧪 Ejecutando todos los tests del sistema...\n");

        TestUsuarios.main(args);
        System.out.println("✅ TestUsuarios ejecutado.\n");

        TestRecurso.main(args);
        System.out.println("✅ TestRecursos ejecutado.\n");

        TestPrestamoDevolucion.main(args);
        System.out.println("✅ TestPrestamoDevolucion ejecutado.\n");

        TestDigitales.main(args);
        System.out.println("✅ TestDigitales ejecutado.\n");

        TestAudiolibro.main(args);
        System.out.println("✅ TestAudiolibro ejecutado.\n");

        TestVisualizacion.main(args);
        System.out.println("✅ TestVisualizacion ejecutado.\n");

        TestNotificacion.main(args);
        System.out.println("✅ TestNotificacion ejecutado.\n");

        TestIntegrador.main(args);
        System.out.println("✅ TestIntegrador ejecutado.\n");

        System.out.println("🎉 Todos los tests fueron ejecutados exitosamente.");
    }
}
