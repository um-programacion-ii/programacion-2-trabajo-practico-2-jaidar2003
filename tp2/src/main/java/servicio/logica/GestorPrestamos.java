package servicio.logica;

import excepciones.RecursoNoDisponibleException;
import modelo.Prestamo;
import modelo.Usuario;
import modelo.AlertaVencimiento;
import interfaz.Prestable;
import interfaz.RecursoDigital;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorPrestamos {
    private final List<Prestamo> prestamos = new ArrayList<>();

    public void registrarPrestamo(Usuario usuario, RecursoDigital recurso) throws RecursoNoDisponibleException {
        if (recurso instanceof Prestable prestable && prestable.estaDisponible()) {
            prestable.prestar(); // Cambiar el estado a PRESTADO
            Prestamo nuevoPrestamo = new Prestamo(usuario, recurso, LocalDate.now(), LocalDate.now().plusDays(7));
            prestamos.add(nuevoPrestamo);
            System.out.println("✅ Préstamo registrado exitosamente.");
        } else {
            throw new RecursoNoDisponibleException("El recurso no está disponible para préstamo.");
        }
    }

    public void listarPrestamos() {
        if (prestamos.isEmpty()) {
            System.out.println("📭 No hay préstamos registrados.");
        } else {
            prestamos.forEach(System.out::println);
        }
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void verificarVencimientos() {
        System.out.println("🔍 Verificando vencimientos de préstamos...\n");

        boolean hayAlertas = false;

        for (Prestamo prestamo : prestamos) {
            AlertaVencimiento alerta = new AlertaVencimiento(prestamo);
            switch (alerta.getUrgencia()) {
                case WARNING, CRITICAL -> {
                    System.out.println(alerta.getMensaje());
                    hayAlertas = true;
                }
                default -> {} // No mostrar INFO
            }
        }

        if (!hayAlertas) {
            System.out.println("✅ No hay préstamos próximos a vencer.");
        }
    }
}
