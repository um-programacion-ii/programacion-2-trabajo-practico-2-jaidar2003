package servicio.logica;

import modelo.Prestamo;
import modelo.Usuario;
import interfaz.RecursoDigital;
import recurso.RecursoBase;

import java.util.*;
import java.util.stream.Collectors;

public class GestorReportes {

    private final List<Prestamo> prestamos;

    public GestorReportes(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
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
}
