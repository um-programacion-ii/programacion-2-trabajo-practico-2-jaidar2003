package servicio.logica;

import excepciones.RecursoNoDisponibleException;
import interfaz.*;
import modelo.*;

import java.util.*;

public class GestorRecursos {
    private final Map<String, RecursoDigital> recursos = new HashMap<>();
    private final ServicioNotificaciones servicioNotificaciones;

    public GestorRecursos(ServicioNotificaciones servicioNotificaciones) {
        this.servicioNotificaciones = servicioNotificaciones;
    }

    public void registrarRecurso(RecursoDigital recurso) {
        recursos.put(recurso.getIdentificador(), recurso);
    }

    public void listarRecursos() {
        if (recursos.isEmpty()) {
            System.out.println("📭 No hay recursos registrados.");
            return;
        }
        recursos.values().forEach(System.out::println);
    }

    public synchronized void prestar(String id) throws RecursoNoDisponibleException {
        RecursoDigital recurso = buscarPorId(id);
        if (recurso instanceof Prestable prestable) {
            if (prestable.estaDisponible()) {
                prestable.prestar();
                servicioNotificaciones.enviar("🔔 El recurso " + recurso.getTitulo() + " ha sido prestado.");
                System.out.println("✅ Recurso prestado.");
            } else {
                throw new RecursoNoDisponibleException("El recurso ya está prestado.");
            }
        } else {
            throw new RecursoNoDisponibleException("El recurso no es prestable o no existe.");
        }
    }

    public void devolver(String id) throws RecursoNoDisponibleException {
        RecursoDigital recurso = buscarPorId(id);
        if (recurso instanceof Prestable prestable) {
            prestable.devolver();
            servicioNotificaciones.enviar("🔔 El recurso " + recurso.getTitulo() + " ha sido devuelto.");
            System.out.println("✅ Recurso devuelto.");
        } else {
            throw new RecursoNoDisponibleException("❌ El recurso no se puede devolver o no existe.");
        }
    }

    public void renovar(String id) throws RecursoNoDisponibleException {
        RecursoDigital recurso = buscarPorId(id);
        if (recurso instanceof Renovable renovable) {
            renovable.renovar();
            servicioNotificaciones.enviar("🔄 El recurso " + recurso.getTitulo() + " ha sido renovado.");
        } else {
            throw new RecursoNoDisponibleException("❌ El recurso no es renovable o no existe.");
        }
    }

    public void accederOnline(String id) throws RecursoNoDisponibleException {
        RecursoDigital recurso = buscarPorId(id);
        if (recurso instanceof Accesible accesible) {
            accesible.accederEnLinea();
        } else {
            throw new RecursoNoDisponibleException("❌ El recurso no permite acceso en línea o no existe.");
        }
    }

    public void descargar(String id) throws RecursoNoDisponibleException {
        RecursoDigital recurso = buscarPorId(id);
        if (recurso instanceof Accesible accesible) {
            accesible.descargar();
        } else {
            throw new RecursoNoDisponibleException("❌ El recurso no se puede descargar o no existe.");
        }
    }

    private RecursoDigital buscarPorId(String id) throws RecursoNoDisponibleException {
        RecursoDigital recurso = recursos.get(id);
        if (recurso == null) {
            throw new RecursoNoDisponibleException("❌ No se encontró un recurso con ID: " + id);
        }
        return recurso;
    }

    public List<RecursoDigital> buscarPorTitulo(String titulo) {
        return recursos.values().stream()
                .filter(r -> r.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .toList();
    }

    public List<RecursoDigital> filtrarPorCategoria(CategoriaRecurso categoria) {
        return recursos.values().stream()
                .filter(r -> r.getCategoria().equals(categoria))
                .toList();
    }

    public List<RecursoDigital> ordenarPorTituloAscendente() {
        return recursos.values().stream()
                .sorted(Comparator.comparing(RecursoDigital::getTitulo))
                .toList();
    }

    public void mostrarLista(List<RecursoDigital> lista) {
        if (lista.isEmpty()) {
            System.out.println("📭 No se encontraron recursos.");
        } else {
            lista.forEach(System.out::println);
        }
    }
}
