package servicio.logica;

import interfaz.*;
import modelo.*;
import excepciones.RecursoNoDisponibleException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GestorRecursos {
    private final List<interfazRecursoDigital> recursos = new ArrayList<>();
    private final ServicioNotificaciones servicioNotificaciones;

    public GestorRecursos(ServicioNotificaciones servicioNotificaciones) {
        this.servicioNotificaciones = servicioNotificaciones;
    }

    public void registrarRecurso(interfazRecursoDigital recurso) {
        recursos.add(recurso);
    }

    public void listarRecursos() {
        if (recursos.isEmpty()) {
            System.out.println("📭 No hay recursos registrados.");
            return;
        }
        for (interfazRecursoDigital r : recursos) {
            System.out.println(r);
        }
    }

    public void prestar(String id) throws RecursoNoDisponibleException {
        synchronized (this) { // 🔐 Bloque sincronizado
            interfazRecursoDigital recurso = buscarPorId(id);
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
    }


    public void devolver(String id) throws RecursoNoDisponibleException {
        interfazRecursoDigital recurso = buscarPorId(id);
        if (recurso instanceof Prestable prestable) {
            prestable.devolver();
            servicioNotificaciones.enviar("🔔 El recurso " + recurso.getTitulo() + " ha sido devuelto.");
            System.out.println("✅ Recurso devuelto.");
        } else {
            throw new RecursoNoDisponibleException("❌ El recurso no se puede devolver o no existe.");
        }
    }

    public void renovar(String id) throws RecursoNoDisponibleException {
        interfazRecursoDigital recurso = buscarPorId(id);
        if (recurso instanceof Renovable renovable) {
            renovable.renovar();
            servicioNotificaciones.enviar("🔄 El recurso " + recurso.getTitulo() + " ha sido renovado.");
        } else {
            throw new RecursoNoDisponibleException("❌ El recurso no es renovable o no existe.");
        }
    }

    public void accederOnline(String id) throws RecursoNoDisponibleException {
        interfazRecursoDigital recurso = buscarPorId(id);
        if (recurso instanceof Accesible accesible) {
            accesible.accederEnLinea();
        } else {
            throw new RecursoNoDisponibleException("❌ El recurso no permite acceso en línea o no existe.");
        }
    }

    public void descargar(String id) throws RecursoNoDisponibleException {
        interfazRecursoDigital recurso = buscarPorId(id);
        if (recurso instanceof Accesible accesible) {
            accesible.descargar();
        } else {
            throw new RecursoNoDisponibleException("❌ El recurso no se puede descargar o no existe.");
        }
    }

    private interfazRecursoDigital buscarPorId(String id) throws RecursoNoDisponibleException {
        return recursos.stream()
                .filter(r -> r.getIdentificador().equals(id))
                .findFirst()
                .orElseThrow(() -> new RecursoNoDisponibleException("❌ No se encontró un recurso con ID: " + id));
    }

    // 🔎 Búsqueda por título con Streams
    public List<interfazRecursoDigital> buscarPorTitulo(String titulo) {
        return recursos.stream()
                .filter(r -> r.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .toList();
    }

    // 🗂️ Filtro por categoría
    public List<interfazRecursoDigital> filtrarPorCategoria(CategoriaRecurso categoria) {
        return recursos.stream()
                .filter(r -> r.getCategoria().equals(categoria))
                .toList();
    }

    // 🔤 Ordenar por título (ascendente)
    public List<interfazRecursoDigital> ordenarPorTituloAscendente() {
        return recursos.stream()
                .sorted(Comparator.comparing(interfazRecursoDigital::getTitulo))
                .toList();
    }

    // 📋 Mostrar lista de recursos
    public void mostrarLista(List<interfazRecursoDigital> lista) {
        if (lista.isEmpty()) {
            System.out.println("📭 No se encontraron recursos.");
            return;
        }
        lista.forEach(System.out::println);
    }
}
