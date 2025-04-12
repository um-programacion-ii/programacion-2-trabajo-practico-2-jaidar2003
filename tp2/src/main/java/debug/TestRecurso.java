package debug;

import modelo.Usuario;
import servicio.GestorUsuarios;

public class TestRecurso {
    public static void main(String[] args) {
        // Crear gestor
        GestorUsuarios gestor = new GestorUsuarios();

        // Registrar usuarios
        Usuario u1 = new Usuario("1", "Juanma", "juanma@mail.com");
        Usuario u2 = new Usuario("2", "Laura", "laura@mail.com");

        gestor.registrarUsuario(u1);
        gestor.registrarUsuario(u2);

        // Listar usuarios
        System.out.println("📋 Listado de usuarios:");
        gestor.listarUsuarios();

        // Buscar usuario por ID
        System.out.println("\n🔍 Buscar usuario con ID '1':");
        Usuario encontrado = gestor.buscarPorId("1");
        System.out.println(encontrado);

        // Buscar uno inexistente
        System.out.println("\n🔍 Buscar usuario con ID '99':");
        Usuario noExiste = gestor.buscarPorId("99");
        System.out.println(noExiste != null ? noExiste : "No existe ese usuario.");
    }
}
