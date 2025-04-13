package test;

import interfaz.ServicioNotificaciones;
import recurso.*;
import servicio.notificacion.ServicioNotificacionesSMS;
import servicio.logica.GestorRecursos;

public class TestNotificacionesSMS {
    public static void main(String[] args) {
        System.out.println("📲 Test con notificaciones SMS\n");

        // ✅ Crear implementación de notificaciones SMS
        ServicioNotificaciones servicioSMS = new ServicioNotificacionesSMS();

        // ✅ Inyectar en el gestor
        GestorRecursos gestor = new GestorRecursos(servicioSMS);

        // ✅ Registrar recursos
        gestor.registrarRecurso(new Libro("L1", "1984", "George Orwell", "978-0141036144"));
        gestor.registrarRecurso(new Audiolibro("A1", "Los Miserables", "Carlos Pérez"));

        // ✅ Prestar, renovar y devolver
        gestor.prestar("L1");
        gestor.renovar("L1");
        gestor.devolver("L1");

        // ✅ Acceso online y descarga
        gestor.accederOnline("A1");
        gestor.descargar("A1");

        System.out.println("\n✅ TestNotificacionesSMS ejecutado correctamente.");
    }
}
