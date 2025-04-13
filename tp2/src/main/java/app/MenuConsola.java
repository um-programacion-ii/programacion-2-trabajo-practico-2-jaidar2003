package app;

import modelo.Usuario;
import recurso.*;
import servicio.logica.GestorRecursos;
import servicio.logica.GestorUsuarios;
import servicio.notificacion.ServicioNotificacionesEmail;
import java.util.Scanner;

public class MenuConsola {
    private static final Scanner scanner = new Scanner(System.in);
    private static final GestorUsuarios gestorUsuarios = new GestorUsuarios();
    private static final GestorRecursos gestorRecursos = new GestorRecursos(new ServicioNotificacionesEmail());
    // Para probar SMS, cambiá a:
    // private static final GestorRecursos gestorRecursos = new GestorRecursos(new ServicioNotificacionesSMS());


    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> registrarUsuario();
                case 2 -> listarUsuarios();
                case 3 -> registrarRecurso();
                case 4 -> listarRecursos();
                case 5 -> prestarRecurso();
                case 6 -> devolverRecurso();
                case 7 -> renovarRecurso();
                case 8 -> accederOnline();
                case 9 -> descargarRecurso();
                case 0 -> System.out.println("👋 ¡Hasta luego!");
                default -> System.out.println("❌ Opción inválida.");
            }

        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("""
            📚 Sistema de Gestión de Biblioteca

            1️⃣ Registrar usuario
            2️⃣ Listar usuarios
            3️⃣ Registrar recurso
            4️⃣ Listar recursos
            5️⃣ Prestar recurso
            6️⃣ Devolver recurso
            7️⃣ Renovar recurso
            8️⃣ Acceder en línea a recurso digital
            9️⃣ Descargar recurso digital
            0️⃣ Salir
            """);
        System.out.print("Seleccione una opción: ");
    }

    private static void registrarUsuario() {
        System.out.print("Ingrese ID: ");
        String id = scanner.nextLine();
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese email: ");
        String email = scanner.nextLine();

        gestorUsuarios.registrarUsuario(new Usuario(id, nombre, email));
        System.out.println("✅ Usuario registrado con éxito.");
    }

    private static void listarUsuarios() {
        gestorUsuarios.listarUsuarios();
    }

    private static void registrarRecurso() {
        System.out.print("Tipo (libro/revista/audiolibro): ");
        String tipo = scanner.nextLine().toLowerCase();
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        switch (tipo) {
            case "libro" -> {
                System.out.print("Autor: ");
                String autor = scanner.nextLine();
                System.out.print("ISBN: ");
                String isbn = scanner.nextLine();
                gestorRecursos.registrarRecurso(new Libro(id, titulo, autor, isbn));
            }
            case "revista" -> {
                System.out.print("Número de edición: ");
                int edicion = scanner.nextInt();
                scanner.nextLine();
                gestorRecursos.registrarRecurso(new Revista(id, titulo, edicion));
            }
            case "audiolibro" -> {
                System.out.print("Narrador: ");
                String narrador = scanner.nextLine();
                gestorRecursos.registrarRecurso(new Audiolibro(id, titulo, narrador));
            }
            default -> System.out.println("❌ Tipo de recurso no válido.");
        }
    }

    private static void listarRecursos() {
        gestorRecursos.listarRecursos();
    }

    private static void prestarRecurso() {
        System.out.print("ID del recurso: ");
        String id = scanner.nextLine();
        gestorRecursos.prestar(id);
    }

    private static void devolverRecurso() {
        System.out.print("ID del recurso: ");
        String id = scanner.nextLine();
        gestorRecursos.devolver(id);
    }

    private static void renovarRecurso() {
        System.out.print("ID del recurso: ");
        String id = scanner.nextLine();
        gestorRecursos.renovar(id);
    }

    private static void accederOnline() {
        System.out.print("ID del recurso digital: ");
        String id = scanner.nextLine();
        gestorRecursos.accederOnline(id);
    }

    private static void descargarRecurso() {
        System.out.print("ID del recurso digital: ");
        String id = scanner.nextLine();
        gestorRecursos.descargar(id);
    }
}
