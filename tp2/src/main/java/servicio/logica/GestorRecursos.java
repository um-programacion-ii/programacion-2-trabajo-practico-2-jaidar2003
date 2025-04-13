package servicio.logica;

import interfaz.*;
import modelo.EstadoRecurso;
import recurso.RecursoBase;

import java.util.ArrayList;
import java.util.List;

public class GestorRecursos {
    private final List<RecursoBase> recursos = new ArrayList<>();
    private final ServicioNotificaciones servicioNotificaciones;

    public GestorRecursos(ServicioNotificaciones servicioNotificaciones) {
        this.servicioNotificaciones = servicioNotificaciones;
    }

    public void registrarRecurso(RecursoBase recurso) {
        recursos.add(recurso);
    }

    public void listarRecursos() {
        if (recursos.isEmpty()) {
            System.out.println("📭 No hay recursos registrados.");
            return;
        }
        for (RecursoBase r : recursos) {
            System.out.println(r);
        }
    }

    public void prestar(String id) {
        RecursoBase recurso = buscarPorId(id);
        if (recurso instanceof Prestable prestable) {
            prestable.prestar();
            servicioNotificaciones.enviar("🔔 El recurso " + recurso.getTitulo() + " ha sido prestado.");
            System.out.println("✅ Recurso prestado.");
        } else {
            System.out.println("❌ El recurso no se puede prestar o no existe.");
        }
    }

    public void devolver(String id) {
        RecursoBase recurso = buscarPorId(id);
        if (recurso instanceof Prestable prestable) {
            prestable.devolver();
            servicioNotificaciones.enviar("🔔 El recurso " + recurso.getTitulo() + " ha sido devuelto.");
            System.out.println("✅ Recurso devuelto.");
        } else {
            System.out.println("❌ El recurso no se puede devolver o no existe.");
        }
    }

    public void renovar(String id) {
        RecursoBase recurso = buscarPorId(id);
        if (recurso instanceof Renovable renovable) {
            renovable.renovar();
            servicioNotificaciones.enviar("🔄 El recurso " + recurso.getTitulo() + " ha sido renovado.");
        } else {
            System.out.println("❌ El recurso no es renovable o no existe.");
        }
    }

    public void accederOnline(String id) {
        RecursoBase recurso = buscarPorId(id);
        if (recurso instanceof Accesible accesible) {
            accesible.accederEnLinea();
        } else {
            System.out.println("❌ El recurso no permite acceso en línea o no existe.");
        }
    }

    public void descargar(String id) {
        RecursoBase recurso = buscarPorId(id);
        if (recurso instanceof Accesible accesible) {
            accesible.descargar();
        } else {
            System.out.println("❌ El recurso no se puede descargar o no existe.");
        }
    }

    private RecursoBase buscarPorId(String id) {
        for (RecursoBase r : recursos) {
            if (r.getIdentificador().equals(id)) {
                return r;
            }
        }
        return null;
    }
}
