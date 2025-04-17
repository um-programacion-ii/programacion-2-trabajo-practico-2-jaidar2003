package servicio.logica;

import modelo.Reserva;
import modelo.Usuario;
import interfaz.interfazRecursoDigital;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class GestorReservas {
    private final BlockingQueue<Reserva> reservas = new LinkedBlockingQueue<>();

    public void agregarReserva(Usuario usuario, interfazRecursoDigital recurso) throws InterruptedException {
        Reserva reserva = new Reserva(usuario, recurso);
        reservas.put(reserva);
        System.out.println("📥 Reserva agregada para: " + usuario.getNombre() + " - " + recurso.getTitulo());
    }

    public void mostrarReservasPendientes() {
        if (reservas.isEmpty()) {
            System.out.println("📭 No hay reservas pendientes.");
            return;
        }

        System.out.println("📋 Reservas pendientes:");
        reservas.forEach(System.out::println);
    }

    public void liberarRecurso(interfazRecursoDigital recurso) throws InterruptedException {
        List<Reserva> pendientes = reservas.stream()
                .filter(r -> r.getRecurso().equals(recurso))
                .collect(Collectors.toList());

        for (Reserva r : pendientes) {
            reservas.remove(r);
            System.out.println("✅ Recurso disponible para: " + r.getUsuario().getNombre());
        }
    }
}
