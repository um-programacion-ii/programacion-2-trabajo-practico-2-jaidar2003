package servicio.logica;

import modelo.Prestamo;
import modelo.Usuario;
import interfaz.RecursoDigital;
import recurso.RecursoBase;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GestorReportes {

    private final List<Prestamo> prestamos;
    private final ExecutorService executorService;
    private final Map<String, Future<?>> reportesEnProceso;
    private final Map<String, Integer> progresoReportes;
    private final Map<String, String> resultadosReportes;

    public GestorReportes(List<Prestamo> prestamos) {
        this.prestamos = Collections.synchronizedList(new ArrayList<>(prestamos));
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.reportesEnProceso = new ConcurrentHashMap<>();
        this.progresoReportes = new ConcurrentHashMap<>();
        this.resultadosReportes = new ConcurrentHashMap<>();
    }

    // 📊 Recursos más prestados
    public void mostrarRecursosMasPrestados() {
        System.out.println("\n📈 Recursos más prestados:");

        Map<String, Long> contador = prestamos.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getRecurso().getTitulo(),
                        Collectors.counting()
                ));

        contador.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(e -> System.out.printf("📚 %s - %d veces\n", e.getKey(), e.getValue()));
    }

    // 👤 Usuarios más activos
    public void mostrarUsuariosMasActivos() {
        System.out.println("\n👥 Usuarios más activos:");

        Map<String, Long> contador = prestamos.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getUsuario().getNombre(),
                        Collectors.counting()
                ));

        contador.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(e -> System.out.printf("🙋 %s - %d préstamos\n", e.getKey(), e.getValue()));
    }

    // 📚 Estadísticas por categoría
    public void mostrarEstadisticasPorCategoria() {
        System.out.println("\n📊 Estadísticas de uso por categoría:");

        Map<String, Long> contador = prestamos.stream()
                .collect(Collectors.groupingBy(
                        p -> {
                            RecursoDigital recurso = p.getRecurso();
                            if (recurso instanceof RecursoBase base) {
                                return base.getCategoria().toString();
                            } else {
                                return "SIN_CATEGORÍA";
                            }
                        },
                        Collectors.counting()
                ));

        contador.forEach((categoria, total) ->
                System.out.printf("📁 %s: %d préstamos\n", categoria, total)
        );
    }

    // 🧾 Mostrar todos los reportes
    public void mostrarTodosLosReportes() {
        mostrarRecursosMasPrestados();
        mostrarUsuariosMasActivos();
        mostrarEstadisticasPorCategoria();
    }

    /**
     * Genera un reporte de recursos más prestados de forma asíncrona.
     * @param reporteId Identificador único para el reporte
     * @return Future que representa la tarea asíncrona
     */
    public Future<?> generarReporteRecursosMasPrestadosAsync(String reporteId) {
        progresoReportes.put(reporteId, 0);
        Future<?> future = executorService.submit(() -> {
            try {
                StringBuilder resultado = new StringBuilder("\n📈 Recursos más prestados:\n");

                // Simulamos progreso
                actualizarProgreso(reporteId, 25);
                Thread.sleep(1000); // Simulamos procesamiento

                synchronized (prestamos) {
                    Map<String, Long> contador = prestamos.stream()
                            .collect(Collectors.groupingBy(
                                    p -> p.getRecurso().getTitulo(),
                                    Collectors.counting()
                            ));

                    actualizarProgreso(reporteId, 50);
                    Thread.sleep(500); // Simulamos más procesamiento

                    contador.entrySet().stream()
                            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                            .limit(5)
                            .forEach(e -> resultado.append(String.format("📚 %s - %d veces\n", e.getKey(), e.getValue())));
                }

                actualizarProgreso(reporteId, 100);
                resultadosReportes.put(reporteId, resultado.toString());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                resultadosReportes.put(reporteId, "❌ Reporte interrumpido: " + e.getMessage());
            } finally {
                reportesEnProceso.remove(reporteId);
            }
        });

        reportesEnProceso.put(reporteId, future);
        return future;
    }

    /**
     * Genera un reporte de usuarios más activos de forma asíncrona.
     * @param reporteId Identificador único para el reporte
     * @return Future que representa la tarea asíncrona
     */
    public Future<?> generarReporteUsuariosMasActivosAsync(String reporteId) {
        progresoReportes.put(reporteId, 0);
        Future<?> future = executorService.submit(() -> {
            try {
                StringBuilder resultado = new StringBuilder("\n👥 Usuarios más activos:\n");

                // Simulamos progreso
                actualizarProgreso(reporteId, 25);
                Thread.sleep(800); // Simulamos procesamiento

                synchronized (prestamos) {
                    Map<String, Long> contador = prestamos.stream()
                            .collect(Collectors.groupingBy(
                                    p -> p.getUsuario().getNombre(),
                                    Collectors.counting()
                            ));

                    actualizarProgreso(reporteId, 50);
                    Thread.sleep(700); // Simulamos más procesamiento

                    contador.entrySet().stream()
                            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                            .limit(5)
                            .forEach(e -> resultado.append(String.format("🙋 %s - %d préstamos\n", e.getKey(), e.getValue())));
                }

                actualizarProgreso(reporteId, 100);
                resultadosReportes.put(reporteId, resultado.toString());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                resultadosReportes.put(reporteId, "❌ Reporte interrumpido: " + e.getMessage());
            } finally {
                reportesEnProceso.remove(reporteId);
            }
        });

        reportesEnProceso.put(reporteId, future);
        return future;
    }

    /**
     * Genera un reporte de estadísticas por categoría de forma asíncrona.
     * @param reporteId Identificador único para el reporte
     * @return Future que representa la tarea asíncrona
     */
    public Future<?> generarReporteEstadisticasPorCategoriaAsync(String reporteId) {
        progresoReportes.put(reporteId, 0);
        Future<?> future = executorService.submit(() -> {
            try {
                StringBuilder resultado = new StringBuilder("\n📊 Estadísticas de uso por categoría:\n");

                // Simulamos progreso
                actualizarProgreso(reporteId, 25);
                Thread.sleep(1200); // Simulamos procesamiento

                synchronized (prestamos) {
                    Map<String, Long> contador = prestamos.stream()
                            .collect(Collectors.groupingBy(
                                    p -> {
                                        RecursoDigital recurso = p.getRecurso();
                                        if (recurso instanceof RecursoBase base) {
                                            return base.getCategoria().toString();
                                        } else {
                                            return "SIN_CATEGORÍA";
                                        }
                                    },
                                    Collectors.counting()
                            ));

                    actualizarProgreso(reporteId, 50);
                    Thread.sleep(600); // Simulamos más procesamiento

                    contador.forEach((categoria, total) ->
                            resultado.append(String.format("📁 %s: %d préstamos\n", categoria, total))
                    );
                }

                actualizarProgreso(reporteId, 100);
                resultadosReportes.put(reporteId, resultado.toString());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                resultadosReportes.put(reporteId, "❌ Reporte interrumpido: " + e.getMessage());
            } finally {
                reportesEnProceso.remove(reporteId);
            }
        });

        reportesEnProceso.put(reporteId, future);
        return future;
    }

    /**
     * Genera todos los reportes de forma asíncrona.
     * @return Map con los identificadores de los reportes y sus respectivos Futures
     */
    public Map<String, Future<?>> generarTodosLosReportesAsync() {
        Map<String, Future<?>> reportes = new HashMap<>();

        reportes.put("recursos", generarReporteRecursosMasPrestadosAsync("recursos"));
        reportes.put("usuarios", generarReporteUsuariosMasActivosAsync("usuarios"));
        reportes.put("categorias", generarReporteEstadisticasPorCategoriaAsync("categorias"));

        return reportes;
    }

    /**
     * Actualiza el progreso de un reporte.
     * @param reporteId Identificador del reporte
     * @param progreso Porcentaje de progreso (0-100)
     */
    private void actualizarProgreso(String reporteId, int progreso) {
        progresoReportes.put(reporteId, progreso);
        System.out.printf("⏳ Progreso del reporte '%s': %d%%\n", reporteId, progreso);
    }

    /**
     * Obtiene el progreso actual de un reporte.
     * @param reporteId Identificador del reporte
     * @return Porcentaje de progreso (0-100) o -1 si el reporte no existe
     */
    public int obtenerProgresoReporte(String reporteId) {
        return progresoReportes.getOrDefault(reporteId, -1);
    }

    /**
     * Verifica si un reporte está en proceso.
     * @param reporteId Identificador del reporte
     * @return true si el reporte está en proceso, false en caso contrario
     */
    public boolean reporteEnProceso(String reporteId) {
        return reportesEnProceso.containsKey(reporteId);
    }

    /**
     * Obtiene el resultado de un reporte.
     * @param reporteId Identificador del reporte
     * @return Resultado del reporte o null si no está disponible
     */
    public String obtenerResultadoReporte(String reporteId) {
        return resultadosReportes.get(reporteId);
    }

    /**
     * Muestra el estado actual de todos los reportes.
     */
    public void mostrarEstadoReportes() {
        if (reportesEnProceso.isEmpty() && resultadosReportes.isEmpty()) {
            System.out.println("📭 No hay reportes en proceso ni finalizados.");
            return;
        }

        System.out.println("\n📋 Estado de los reportes:");

        // Reportes en proceso
        if (!reportesEnProceso.isEmpty()) {
            System.out.println("\n⏳ Reportes en proceso:");
            reportesEnProceso.forEach((id, future) -> {
                int progreso = progresoReportes.getOrDefault(id, 0);
                System.out.printf("  - %s: %d%% completado\n", id, progreso);
            });
        }

        // Reportes finalizados
        if (!resultadosReportes.isEmpty()) {
            System.out.println("\n✅ Reportes finalizados:");
            resultadosReportes.keySet().forEach(id -> {
                if (!reportesEnProceso.containsKey(id)) {
                    System.out.printf("  - %s: Disponible\n", id);
                }
            });
        }
    }

    /**
     * Cierra el ExecutorService y libera los recursos.
     */
    public void cerrar() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
            System.out.println("🔌 Servicio de reportes cerrado correctamente.");
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
            System.out.println("❌ Error al cerrar el servicio de reportes: " + e.getMessage());
        }
    }
}
