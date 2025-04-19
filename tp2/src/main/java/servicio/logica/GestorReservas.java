package servicio.logica;

import excepciones.RecursoNoDisponibleException;
import modelo.AlertaDisponibilidad;
import modelo.Reserva;
import modelo.Usuario;
import interfaz.RecursoDigital;
import servicio.notificacion.NotificacionConsola;
import servicio.notificacion.ServicioNotificaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class GestorReservas {
    private final BlockingQueue<Reserva> reservas = new LinkedBlockingQueue<>();
    private final List<AlertaDisponibilidad> alertasDisponibilidad = new ArrayList<>();
    private ServicioNotificaciones servicioNotificaciones = new NotificacionConsola();
    private GestorPrestamos gestorPrestamos;

    public GestorReservas() {
        this.gestorPrestamos = new GestorPrestamos();
    }

    public GestorReservas(GestorPrestamos gestorPrestamos) {
        this.gestorPrestamos = gestorPrestamos;
    }

    public void setServicioNotificaciones(ServicioNotificaciones servicioNotificaciones) {
        this.servicioNotificaciones = servicioNotificaciones;
    }

    public void agregarReserva(Usuario usuario, RecursoDigital recurso) throws InterruptedException {
        Reserva reserva = new Reserva(usuario, recurso);
        reservas.put(reserva);
        System.out.println("📥 Reserva agregada para: " + usuario.getNombre() + " - " + recurso.getTitulo());
    }

    public void mostrarReservas() {
        if (reservas.isEmpty()) {
            System.out.println("📭 No hay reservas registradas.");
            return;
        }

        System.out.println("📋 Reservas registradas:");
        reservas.forEach(System.out::println);
    }

    public void mostrarReservasPendientes() {
        if (reservas.isEmpty()) {
            System.out.println("📭 No hay reservas pendientes.");
            return;
        }

        System.out.println("📋 Reservas pendientes:");
        reservas.forEach(System.out::println);
    }

    public void mostrarRecursosDisponibles(Usuario usuario) {
        List<AlertaDisponibilidad> alertasUsuario = alertasDisponibilidad.stream()
                .filter(a -> a.getUsuario().equals(usuario))
                .collect(Collectors.toList());

        if (alertasUsuario.isEmpty()) {
            System.out.println("📭 No hay recursos disponibles para " + usuario.getNombre());
            return;
        }

        System.out.println("📋 Recursos disponibles para " + usuario.getNombre() + ":");
        for (int i = 0; i < alertasUsuario.size(); i++) {
            AlertaDisponibilidad alerta = alertasUsuario.get(i);
            System.out.println((i + 1) + ". " + alerta.getRecurso().getTitulo());
        }
    }

    public void liberarRecurso(RecursoDigital recurso) throws InterruptedException {
        List<Reserva> pendientes = reservas.stream()
                .filter(r -> r.getRecurso().equals(recurso))
                .collect(Collectors.toList());

        for (Reserva r : pendientes) {
            reservas.remove(r);
            AlertaDisponibilidad alerta = new AlertaDisponibilidad(r);
            alertasDisponibilidad.add(alerta);
            servicioNotificaciones.enviarNotificacion(alerta.getMensaje());
            System.out.println("✅ Recurso disponible para: " + r.getUsuario().getNombre());
        }
    }

    public void procesarAlertasDisponibilidad(boolean modoTest) {
        if (alertasDisponibilidad.isEmpty()) {
            System.out.println("📭 No hay alertas de disponibilidad pendientes.");
            return;
        }

        System.out.println("📋 Alertas de disponibilidad pendientes:");
        for (int i = 0; i < alertasDisponibilidad.size(); i++) {
            AlertaDisponibilidad alerta = alertasDisponibilidad.get(i);
            System.out.println((i + 1) + ". " + alerta.getMensaje());
        }

        if (!modoTest) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n¿Desea realizar un préstamo inmediato? (S/N)");
            String respuesta = scanner.nextLine().trim().toUpperCase();

            if (respuesta.equals("S")) {
                System.out.println("Ingrese el número de la alerta para realizar el préstamo (o 0 para cancelar):");
                int seleccion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                if (seleccion > 0 && seleccion <= alertasDisponibilidad.size()) {
                    AlertaDisponibilidad seleccionada = alertasDisponibilidad.get(seleccion - 1);
                    try {
                        gestorPrestamos.registrarPrestamo(seleccionada.getUsuario(), seleccionada.getRecurso());
                        alertasDisponibilidad.remove(seleccionada);
                        System.out.println("✅ Préstamo realizado con éxito.");
                    } catch (RecursoNoDisponibleException e) {
                        System.out.println("❌ Error: " + e.getMessage());
                    }
                }
            }
        }
    }

    public void procesarAlertasDisponibilidad() {
        procesarAlertasDisponibilidad(false);
    }

    public List<AlertaDisponibilidad> getAlertasDisponibilidad() {
        return alertasDisponibilidad;
    }

    public GestorPrestamos getGestorPrestamos() {
        return gestorPrestamos;
    }
}
