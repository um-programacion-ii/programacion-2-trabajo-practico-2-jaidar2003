package test;

import modelo.CategoriaRecurso;
import modelo.Usuario;
import recurso.Audiolibro;
import recurso.Libro;
import recurso.Revista;
import servicio.logica.GestorRecursos;
import servicio.logica.GestorUsuarios;
import servicio.notificacion.ServicioNotificacionesEmail;
import excepciones.RecursoNoDisponibleException;

public class TestIntegrador {
    public static void main(String[] args) {
        System.out.println("📚 Bienvenido al sistema de gestión de biblioteca\n");

        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        GestorRecursos gestorRecursos = new GestorRecursos(new ServicioNotificacionesEmail());

        System.out.println("👤 Registro de Usuario 1");
        gestorUsuarios.registrarUsuario(new Usuario("U1", "Ana", "ana@mail.com"));

        System.out.println("👤 Registro de Usuario 2");
        gestorUsuarios.registrarUsuario(new Usuario("U2", "Pedro", "pedro@mail.com"));

        gestorRecursos.registrarRecurso(new Libro("L1", "El Principito", "Antoine", "978-0156012195", CategoriaRecurso.FICCION));
        gestorRecursos.registrarRecurso(new Revista("R1", "National Geographic", 202, CategoriaRecurso.CIENCIA));
        gestorRecursos.registrarRecurso(new Audiolibro("A1", "1984", "Carlos Pérez", CategoriaRecurso.HISTORIA));

        try {
            gestorRecursos.prestar("L1");
            gestorRecursos.devolver("L1");

            gestorRecursos.accederOnline("A1");
            gestorRecursos.descargar("A1");
        } catch (RecursoNoDisponibleException e) {
            System.out.println("❌ Error durante una operación de recursos: " + e.getMessage());
        }

        System.out.println("✅ TestIntegrador ejecutado.\n");
    }
}
