package app;

import modelo.PreferenciasNotificacion;
import modelo.Recordatorio;
import modelo.Usuario;
import modelo.CategoriaRecurso;
import recurso.*;
import servicio.logica.GestorPrestamos;
import servicio.logica.GestorRecursos;
import servicio.logica.GestorReportes;
import servicio.logica.GestorReservas;
import servicio.logica.GestorUsuarios;
import servicio.logica.GestorRecordatorios;
import servicio.notificacion.ServicioNotificacionesEmail;
import excepciones.RecursoNoDisponibleException;

import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.Future;

public class MenuConsola {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ServicioNotificacionesEmail servicioNotificaciones = new ServicioNotificacionesEmail();
    private static final GestorUsuarios gestorUsuarios = new GestorUsuarios();
    private static final GestorPrestamos gestorPrestamos = new GestorPrestamos();
    private static final GestorReservas gestorReservas = new GestorReservas(gestorPrestamos);
    private static final GestorRecursos gestorRecursos = new GestorRecursos(servicioNotificaciones);
    private static final GestorRecordatorios sistemaRecordatorios = new GestorRecordatorios();
    private static final GestorReportes gestorReportes = new GestorReportes(gestorPrestamos.getPrestamos());

    static {
        // Configurar el servicio de notificaciones para GestorReservas
        gestorReservas.setServicioNotificaciones(new servicio.notificacion.ServicioNotificacionesEmailAdapter(servicioNotificaciones));
    }

    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> registrarUsuario();
                case 2 -> listarUsuarios();
                case 3 -> registrarRecurso();
                case 4 -> listarRecursos();
                case 5 -> prestarRecurso();
                case 6 -> devolverRecurso();
                case 7 -> renovarRecurso();
                case 8 -> accederOnline();
                case 9 -> descargarRecurso();
                case 10 -> buscarPorCategoria();
                case 11 -> crearRecordatorio();
                case 12 -> verHistorialRecordatorios();
                case 13 -> verRecordatoriosPorUsuario();
                case 14 -> configurarPreferenciasNotificacion();
                case 15 -> verificarVencimientosPrestamos();
                case 16 -> procesarAlertasDisponibilidad();
                case 17 -> generarReportesSincronos();
                case 18 -> generarReportesConcurrentes();
                case 19 -> verEstadoReportes();
                case 20 -> verResultadosReportes();
                case 0 -> {
                    System.out.println("👋 ¡Hasta luego!");
                    servicioNotificaciones.cerrar();  // Cerramos el ExecutorService
                    sistemaRecordatorios.cerrar();    // Cerramos el sistema de recordatorios
                    gestorReportes.cerrar();          // Cerramos el servicio de reportes
                }
                default -> System.out.println("❌ Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("""
            📚 Sistema de Gestión de Biblioteca

            1) Registrar usuario
            2) Listar usuarios
            3) Registrar recurso
            4) Listar recursos
            5) Prestar recurso
            6) Devolver recurso
            7) Renovar recurso
            8) Acceder en línea a recurso digital
            9) Descargar recurso digital
            10) Buscar recursos por categoría
            11) Crear recordatorio
            12) Ver historial de recordatorios
            13) Ver recordatorios por usuario
            14) Configurar preferencias de notificación
            15) Verificar vencimientos de préstamos
            16) Procesar alertas de disponibilidad

            📊 Reportes:
            17) Generar reportes (síncrono)
            18) Generar reportes concurrentes
            19) Ver estado de reportes
            20) Ver resultados de reportes

            0) Salir
            """);
        System.out.print("Seleccione una opción: ");
    }

    private static void registrarUsuario() {
        System.out.print("Ingrese ID: ");
        String id = scanner.nextLine();
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese email: ");
        String email = scanner.nextLine();

        gestorUsuarios.registrarUsuario(new Usuario(id, nombre, email));
        System.out.println("✅ Usuario registrado con éxito.");
    }

    private static void listarUsuarios() {
        gestorUsuarios.listarUsuarios();
    }

    private static void registrarRecurso() {
        System.out.print("Tipo (libro/revista/audiolibro): ");
        String tipo = scanner.nextLine().toLowerCase();
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Categoría (FICCION, TECNICO, HISTORIA, CIENCIA): ");
        String categoriaStr = scanner.nextLine().toUpperCase();

        CategoriaRecurso categoria;
        try {
            categoria = CategoriaRecurso.valueOf(categoriaStr);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Categoría inválida. Recurso no registrado.");
            return;
        }

        switch (tipo) {
            case "libro" -> {
                System.out.print("Autor: ");
                String autor = scanner.nextLine();
                System.out.print("ISBN: ");
                String isbn = scanner.nextLine();
                gestorRecursos.registrarRecurso(new Libro(id, titulo, autor, isbn, categoria));
            }
            case "revista" -> {
                System.out.print("Número de edición: ");
                int edicion = scanner.nextInt();
                scanner.nextLine();
                gestorRecursos.registrarRecurso(new Revista(id, titulo, edicion, categoria));
            }
            case "audiolibro" -> {
                System.out.print("Narrador: ");
                String narrador = scanner.nextLine();
                gestorRecursos.registrarRecurso(new Audiolibro(id, titulo, narrador, categoria));
            }
            default -> System.out.println("❌ Tipo de recurso no válido.");
        }
    }

    private static void listarRecursos() {
        gestorRecursos.listarRecursos();
    }

    private static void prestarRecurso() {
        System.out.print("ID del recurso: ");
        String id = scanner.nextLine();
        try {
            gestorRecursos.prestar(id);
        } catch (RecursoNoDisponibleException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
    }

    private static void devolverRecurso() {
        System.out.print("ID del recurso: ");
        String id = scanner.nextLine();
        try {
            gestorRecursos.devolver(id);
        } catch (RecursoNoDisponibleException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
    }

    private static void renovarRecurso() {
        System.out.print("ID del recurso: ");
        String id = scanner.nextLine();
        try {
            gestorRecursos.renovar(id);
        } catch (RecursoNoDisponibleException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
    }

    private static void accederOnline() {
        System.out.print("ID del recurso digital: ");
        String id = scanner.nextLine();
        try {
            gestorRecursos.accederOnline(id);
        } catch (RecursoNoDisponibleException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
    }

    private static void descargarRecurso() {
        System.out.print("ID del recurso digital: ");
        String id = scanner.nextLine();
        try {
            gestorRecursos.descargar(id);
        } catch (RecursoNoDisponibleException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
    }

    private static void buscarPorCategoria() {
        System.out.println("Categorías disponibles:");
        for (CategoriaRecurso c : CategoriaRecurso.values()) {
            System.out.println("📁 " + c);
        }

        System.out.print("Ingrese categoría a buscar: ");
        String input = scanner.nextLine().toUpperCase();

        try {
            CategoriaRecurso categoria = CategoriaRecurso.valueOf(input);
            var resultados = gestorRecursos.filtrarPorCategoria(categoria);
            gestorRecursos.mostrarLista(resultados);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Categoría inválida.");
        }
    }

    private static void crearRecordatorio() {
        System.out.println("📝 Crear nuevo recordatorio");

        // Seleccionar usuario
        System.out.println("\nUsuarios disponibles:");
        gestorUsuarios.listarUsuarios();

        System.out.print("\nIngrese ID del usuario: ");
        String idUsuario = scanner.nextLine();
        Usuario usuario = gestorUsuarios.buscarUsuarioPorId(idUsuario);

        if (usuario == null) {
            System.out.println("❌ Usuario no encontrado.");
            return;
        }

        // Ingresar datos del recordatorio
        System.out.print("Asunto: ");
        String asunto = scanner.nextLine();

        System.out.print("Mensaje: ");
        String mensaje = scanner.nextLine();

        System.out.println("Nivel de urgencia (1: INFO, 2: WARNING, 3: ERROR): ");
        int nivelOpcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        Recordatorio.NivelUrgencia nivel;
        switch (nivelOpcion) {
            case 1 -> nivel = Recordatorio.NivelUrgencia.INFO;
            case 2 -> nivel = Recordatorio.NivelUrgencia.WARNING;
            case 3 -> nivel = Recordatorio.NivelUrgencia.ERROR;
            default -> {
                System.out.println("❌ Nivel inválido. Se usará INFO por defecto.");
                nivel = Recordatorio.NivelUrgencia.INFO;
            }
        }

        // Crear recordatorio
        sistemaRecordatorios.crearRecordatorio(usuario, asunto, mensaje, nivel);
        System.out.println("✅ Recordatorio creado con éxito.");
    }

    private static void verHistorialRecordatorios() {
        System.out.println("📋 Historial de recordatorios");
        sistemaRecordatorios.mostrarHistorialRecordatorios();
    }

    private static void verRecordatoriosPorUsuario() {
        System.out.println("📋 Recordatorios por usuario");

        // Seleccionar usuario
        System.out.println("\nUsuarios disponibles:");
        gestorUsuarios.listarUsuarios();

        System.out.print("\nIngrese ID del usuario: ");
        String idUsuario = scanner.nextLine();
        Usuario usuario = gestorUsuarios.buscarUsuarioPorId(idUsuario);

        if (usuario == null) {
            System.out.println("❌ Usuario no encontrado.");
            return;
        }

        sistemaRecordatorios.mostrarRecordatoriosUsuario(usuario);
    }

    private static void configurarPreferenciasNotificacion() {
        System.out.println("⚙️ Configurar preferencias de notificación");

        // Seleccionar usuario
        System.out.println("\nUsuarios disponibles:");
        gestorUsuarios.listarUsuarios();

        System.out.print("\nIngrese ID del usuario: ");
        String idUsuario = scanner.nextLine();
        Usuario usuario = gestorUsuarios.buscarUsuarioPorId(idUsuario);

        if (usuario == null) {
            System.out.println("❌ Usuario no encontrado.");
            return;
        }

        System.out.println("Preferencias actuales: " + usuario.getPreferencias());

        // Configurar nuevas preferencias
        System.out.print("¿Recibir notificaciones por email? (S/N): ");
        boolean recibirPorEmail = scanner.nextLine().toUpperCase().equals("S");

        System.out.print("¿Recibir notificaciones por consola? (S/N): ");
        boolean recibirPorConsola = scanner.nextLine().toUpperCase().equals("S");

        System.out.print("¿Recibir notificaciones de nivel INFO? (S/N): ");
        boolean recibirInfoLevel = scanner.nextLine().toUpperCase().equals("S");

        System.out.print("¿Recibir notificaciones de nivel WARNING? (S/N): ");
        boolean recibirWarningLevel = scanner.nextLine().toUpperCase().equals("S");

        System.out.print("¿Recibir notificaciones de nivel ERROR? (S/N): ");
        boolean recibirErrorLevel = scanner.nextLine().toUpperCase().equals("S");

        // Crear y asignar nuevas preferencias
        PreferenciasNotificacion preferencias = new PreferenciasNotificacion(
                recibirPorEmail, recibirPorConsola, recibirInfoLevel, recibirWarningLevel, recibirErrorLevel);

        usuario.setPreferencias(preferencias);
        System.out.println("✅ Preferencias actualizadas con éxito.");
        System.out.println("Nuevas preferencias: " + usuario.getPreferencias());
    }

    /**
     * Verifica los vencimientos de préstamos y permite renovarlos.
     * Utiliza el método verificarVencimientos() de GestorPrestamos.
     */
    private static void verificarVencimientosPrestamos() {
        System.out.println("🔍 Verificando vencimientos de préstamos...");
        gestorPrestamos.verificarVencimientos();
    }

    /**
     * Procesa las alertas de disponibilidad de recursos reservados.
     * Utiliza el método procesarAlertasDisponibilidad() de GestorReservas.
     */
    private static void procesarAlertasDisponibilidad() {
        System.out.println("🔔 Procesando alertas de disponibilidad...");
        gestorReservas.procesarAlertasDisponibilidad();
    }

    /**
     * Genera reportes de forma síncrona.
     * Utiliza el método mostrarTodosLosReportes() de GestorReportes.
     */
    private static void generarReportesSincronos() {
        System.out.println("📊 Generando reportes de forma síncrona...");
        gestorReportes.mostrarTodosLosReportes();
    }

    /**
     * Genera reportes de forma concurrente.
     * Utiliza los métodos de generación asíncrona de GestorReportes.
     */
    private static void generarReportesConcurrentes() {
        System.out.println("📊 Generando reportes de forma concurrente...");

        // Generar un ID único para cada reporte
        String idRecursos = "recursos-" + UUID.randomUUID().toString().substring(0, 8);
        String idUsuarios = "usuarios-" + UUID.randomUUID().toString().substring(0, 8);
        String idCategorias = "categorias-" + UUID.randomUUID().toString().substring(0, 8);

        // Generar reportes de forma asíncrona
        System.out.println("⏳ Iniciando generación de reportes en segundo plano...");

        Future<?> futuroRecursos = gestorReportes.generarReporteRecursosMasPrestadosAsync(idRecursos);
        Future<?> futuroUsuarios = gestorReportes.generarReporteUsuariosMasActivosAsync(idUsuarios);
        Future<?> futuroCategorias = gestorReportes.generarReporteEstadisticasPorCategoriaAsync(idCategorias);

        System.out.println("✅ Reportes iniciados con éxito. Puede consultar su estado con la opción 19.");
        System.out.println("📝 IDs de los reportes:");
        System.out.println("   - Recursos más prestados: " + idRecursos);
        System.out.println("   - Usuarios más activos: " + idUsuarios);
        System.out.println("   - Estadísticas por categoría: " + idCategorias);
    }

    /**
     * Muestra el estado actual de los reportes en proceso y finalizados.
     * Utiliza el método mostrarEstadoReportes() de GestorReportes.
     */
    private static void verEstadoReportes() {
        System.out.println("🔍 Estado de los reportes:");
        gestorReportes.mostrarEstadoReportes();
    }

    /**
     * Permite ver los resultados de un reporte específico.
     * Utiliza el método obtenerResultadoReporte() de GestorReportes.
     */
    private static void verResultadosReportes() {
        System.out.println("📋 Ver resultados de reportes");

        // Mostrar estado actual de reportes
        gestorReportes.mostrarEstadoReportes();

        // Solicitar ID del reporte a consultar
        System.out.print("\nIngrese el ID del reporte que desea ver: ");
        String reporteId = scanner.nextLine();

        // Obtener y mostrar el resultado
        String resultado = gestorReportes.obtenerResultadoReporte(reporteId);

        if (resultado != null) {
            System.out.println("\n📊 Resultado del reporte " + reporteId + ":");
            System.out.println(resultado);
        } else {
            System.out.println("❌ No se encontró un reporte con ese ID o aún no ha finalizado.");
        }
    }
}
