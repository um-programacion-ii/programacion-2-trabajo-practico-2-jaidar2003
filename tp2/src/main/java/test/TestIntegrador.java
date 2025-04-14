package test;

import modelo.CategoriaRecurso;
import modelo.Usuario;
import recurso.Audiolibro;
import recurso.Libro;
import recurso.Revista;
import servicio.logica.GestorRecursos;
import servicio.logica.GestorUsuarios;
import servicio.notificacion.ServicioNotificacionesEmail;

public class TestIntegrador {
    public static void main(String[] args) {
        System.out.println("📚 Bienvenido al sistema de gestión de biblioteca\n");

        // Crear gestores
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        //    GestorRecursos gestorRecursos = new GestorRecursos();
        GestorRecursos gestorRecursos = new GestorRecursos(new ServicioNotificacionesEmail());
    // O para probar SMS:
    // GestorRecursos gestorRecursos = new GestorRecursos(new ServicioNotificacionesSMS());


        // Registrar usuarios
        System.out.println("👤 Registro de Usuario 1");
        gestorUsuarios.registrarUsuario(new Usuario("U1", "Ana", "ana@mail.com"));

        System.out.println("👤 Registro de Usuario 2");
        gestorUsuarios.registrarUsuario(new Usuario("U2", "Pedro", "pedro@mail.com"));

        // Registrar recursos
        gestorRecursos.registrarRecurso(new Libro("L1", "El Principito", "Antoine", "978-0156012195", CategoriaRecurso.ARTE));
        gestorRecursos.registrarRecurso(new Revista("R1", "National Geographic", 202, CategoriaRecurso.ARTE));
        gestorRecursos.registrarRecurso(new Audiolibro("A1", "1984", "Carlos Pérez",  CategoriaRecurso.ARTE));

        // Prestar y devolver recursos
        gestorRecursos.prestar("L1");
        gestorRecursos.devolver("L1");

        // Acceder y descargar digital
        gestorRecursos.accederOnline("A1");
        gestorRecursos.descargar("A1");

        System.out.println("✅ TestIntegrador ejecutado.\n");
    }
}
