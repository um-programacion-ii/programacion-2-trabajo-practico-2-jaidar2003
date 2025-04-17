package servicio.logica;

import excepciones.RecursoNoDisponibleException;
import modelo.Prestamo;
import modelo.Usuario;
import interfaz.Prestable;
import interfaz.interfazRecursoDigital;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorPrestamos {
    private final List<Prestamo> prestamos = new ArrayList<>();

    public void registrarPrestamo(Usuario usuario, interfazRecursoDigital recurso) throws RecursoNoDisponibleException {
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
}
