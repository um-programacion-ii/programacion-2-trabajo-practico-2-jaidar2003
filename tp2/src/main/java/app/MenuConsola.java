package app;

import modelo.Usuario;
import modelo.CategoriaRecurso;
import recurso.*;
import servicio.logica.GestorRecursos;
import servicio.logica.GestorUsuarios;
import servicio.notificacion.ServicioNotificacionesEmail;
import excepciones.RecursoNoDisponibleException;

import java.util.Scanner;

public class MenuConsola {
    private static final Scanner scanner = new Scanner(System.in);
    private static final GestorUsuarios gestorUsuarios = new GestorUsuarios();
    private static final GestorRecursos gestorRecursos = new GestorRecursos(new ServicioNotificacionesEmail());

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
                case 10 -> buscarPorCategoria();
                case 0 -> System.out.println("👋 ¡Hasta luego!");
                default -> System.out.println("❌ Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("""
            📚 Sistema de Gestión de Biblioteca

            1) Registrar usuario
            2) Listar usuarios
            3) Registrar recurso
            4) Listar recursos
            5) Prestar recurso
            6) Devolver recurso
            7) Renovar recurso
            8) Acceder en línea a recurso digital
            9) Descargar recurso digital
            10) Buscar recursos por categoría 
            0) Salir
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

        System.out.print("Categoría (FICCION, TECNICO, HISTORIA, CIENCIA): ");
        String categoriaStr = scanner.nextLine().toUpperCase();

        CategoriaRecurso categoria;
        try {
            categoria = CategoriaRecurso.valueOf(categoriaStr);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Categoría inválida. Recurso no registrado.");
            return;
        }

        switch (tipo) {
            case "libro" -> {
                System.out.print("Autor: ");
                String autor = scanner.nextLine();
                System.out.print("ISBN: ");
                String isbn = scanner.nextLine();
                gestorRecursos.registrarRecurso(new Libro(id, titulo, autor, isbn, categoria));
            }
            case "revista" -> {
                System.out.print("Número de edición: ");
                int edicion = scanner.nextInt();
                scanner.nextLine(); // limpiar buffer
                gestorRecursos.registrarRecurso(new Revista(id, titulo, edicion, categoria));
            }
            case "audiolibro" -> {
                System.out.print("Narrador: ");
                String narrador = scanner.nextLine();
                gestorRecursos.registrarRecurso(new Audiolibro(id, titulo, narrador, categoria));
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
        try {
            gestorRecursos.prestar(id);
        } catch (RecursoNoDisponibleException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
    }

    private static void devolverRecurso() {
        System.out.print("ID del recurso: ");
        String id = scanner.nextLine();
        try {
            gestorRecursos.devolver(id);
        } catch (RecursoNoDisponibleException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
    }

    private static void renovarRecurso() {
        System.out.print("ID del recurso: ");
        String id = scanner.nextLine();
        try {
            gestorRecursos.renovar(id);
        } catch (RecursoNoDisponibleException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
    }

    private static void accederOnline() {
        System.out.print("ID del recurso digital: ");
        String id = scanner.nextLine();
        try {
            gestorRecursos.accederOnline(id);
        } catch (RecursoNoDisponibleException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
    }

    private static void descargarRecurso() {
        System.out.print("ID del recurso digital: ");
        String id = scanner.nextLine();
        try {
            gestorRecursos.descargar(id);
        } catch (RecursoNoDisponibleException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
    }

    private static void buscarPorCategoria() {
        System.out.println("Categorías disponibles:");
        for (CategoriaRecurso c : CategoriaRecurso.values()) {
            System.out.println("📁 " + c);
        }

        System.out.print("Ingrese categoría a buscar: ");
        String input = scanner.nextLine().toUpperCase();

        try {
            CategoriaRecurso categoria = CategoriaRecurso.valueOf(input);
            var resultados = gestorRecursos.filtrarPorCategoria(categoria);
            gestorRecursos.mostrarLista(resultados);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Categoría inválida.");
        }
    }
}
