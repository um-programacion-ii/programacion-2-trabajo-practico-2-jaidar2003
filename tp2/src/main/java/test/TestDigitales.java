package test;

import modelo.CategoriaRecurso;
import modelo.Usuario;
import recurso.Audiolibro;
import recurso.Libro;
import interfaz.RecursoDigital;
import servicio.logica.GestorRecursos;
import servicio.logica.GestorUsuarios;
import servicio.notificacion.ServicioNotificacionesEmail;
import servicio.visualizacion.VisualizadorRecursos;
import excepciones.RecursoNoDisponibleException;

import java.util.List;

/**
 * Test para recursos digitales que prueba diferentes tipos de recursos digitales,
 * operaciones, y manejo de errores.
 */
public class TestDigitales {
    public static void main(String[] args) {
        System.out.println("\n🔍 TEST: Recursos Digitales");
        System.out.println("=========================");

        // Crear gestores y servicios
        ServicioNotificacionesEmail servicioEmail = new ServicioNotificacionesEmail();
        GestorRecursos gestor = new GestorRecursos(servicioEmail);
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        VisualizadorRecursos visualizador = new VisualizadorRecursos();

        // Crear usuarios para pruebas
        Usuario usuario1 = new Usuario("U1", "Ana", "ana@mail.com");
        Usuario usuario2 = new Usuario("U2", "Carlos", "carlos@mail.com");
        gestorUsuarios.registrarUsuario(usuario1);
        gestorUsuarios.registrarUsuario(usuario2);

        // Registrar diferentes tipos de recursos digitales
        System.out.println("\n📚 Registrando recursos digitales...");

        // Audiolibros (implementan RecursoDigital y Accesible)
        Audiolibro audiolibro1 = new Audiolibro("A1", "Los Miserables", "Luis Pérez", CategoriaRecurso.LITERATURA);
        Audiolibro audiolibro2 = new Audiolibro("A2", "El Principito", "María Gómez", CategoriaRecurso.FICCION);

        // Libros (algunos pueden ser digitales en un sistema real)
        Libro libroDigital = new Libro("LD1", "Matemáticas Avanzadas", "John Smith", "ISBN-123", CategoriaRecurso.TECNOLOGIA);

        // Registrar recursos
        gestor.registrarRecurso(audiolibro1);
        gestor.registrarRecurso(audiolibro2);
        gestor.registrarRecurso(libroDigital);

        // Listar todos los recursos
        System.out.println("\n📋 Listado de todos los recursos:");
        gestor.listarRecursos();

        // Prueba 1: Acceso en línea a recursos digitales
        System.out.println("\n🧪 Prueba 1: Acceso en línea a recursos digitales");
        try {
            System.out.println("🌐 Accediendo a 'Los Miserables' en línea...");
            gestor.accederOnline("A1");

            System.out.println("🌐 Accediendo a 'El Principito' en línea...");
            gestor.accederOnline("A2");
        } catch (RecursoNoDisponibleException e) {
            System.out.println("❌ Error al acceder en línea: " + e.getMessage());
        }

        // Prueba 2: Descarga de recursos digitales
        System.out.println("\n🧪 Prueba 2: Descarga de recursos digitales");
        try {
            System.out.println("💾 Descargando 'Los Miserables'...");
            gestor.descargar("A1");

            System.out.println("💾 Descargando 'El Principito'...");
            gestor.descargar("A2");
        } catch (RecursoNoDisponibleException e) {
            System.out.println("❌ Error al descargar: " + e.getMessage());
        }

        // Prueba 3: Filtrado de recursos por categoría
        System.out.println("\n🧪 Prueba 3: Filtrado de recursos por categoría");
        System.out.println("🔍 Buscando recursos de FICCION:");
        List<RecursoDigital> recursosFiccion = gestor.filtrarPorCategoria(CategoriaRecurso.FICCION);
        gestor.mostrarLista(recursosFiccion);

        System.out.println("\n🔍 Buscando recursos de LITERATURA:");
        List<RecursoDigital> recursosLiteratura = gestor.filtrarPorCategoria(CategoriaRecurso.LITERATURA);
        gestor.mostrarLista(recursosLiteratura);

        // Prueba 4: Visualización de recursos
        System.out.println("\n🧪 Prueba 4: Visualización de recursos");
        System.out.println("👁️ Visualizando audiolibro con formato personalizado:");
        System.out.println(audiolibro1);

        System.out.println("\n👁️ Visualizando libro digital con formato personalizado:");
        System.out.println(libroDigital);

        // Prueba 5: Manejo de errores
        System.out.println("\n🧪 Prueba 5: Manejo de errores");

        // Caso 1: Intentar acceder a un recurso que no existe
        try {
            System.out.println("❓ Intentando acceder a un recurso inexistente...");
            gestor.accederOnline("NOEXISTE");
        } catch (RecursoNoDisponibleException e) {
            System.out.println("✅ Error capturado correctamente: " + e.getMessage());
        }

        // Caso 2: Intentar descargar un recurso que no es digital
        try {
            System.out.println("❓ Intentando descargar un libro físico (no digital)...");
            // En un sistema real, podríamos tener libros que no son digitales
            // Aquí simulamos el error intentando descargar un recurso que no implementa la interfaz correcta
            gestor.descargar("LD1");
        } catch (RecursoNoDisponibleException e) {
            System.out.println("✅ Error capturado correctamente: " + e.getMessage());
        }

        // Prueba 6: Búsqueda por título
        System.out.println("\n🧪 Prueba 6: Búsqueda por título");
        System.out.println("🔍 Buscando recursos que contengan 'Principito':");
        List<RecursoDigital> resultadosBusqueda = gestor.buscarPorTitulo("Principito");
        gestor.mostrarLista(resultadosBusqueda);

        // Prueba 7: Ordenamiento de recursos
        System.out.println("\n🧪 Prueba 7: Ordenamiento de recursos por título");
        System.out.println("📋 Recursos ordenados alfabéticamente:");
        List<RecursoDigital> recursosOrdenados = gestor.ordenarPorTituloAscendente();
        gestor.mostrarLista(recursosOrdenados);

        // Cerrar servicios
        servicioEmail.cerrar();

        System.out.println("\n✅ TestDigitales ejecutado con éxito.");
    }
}
