package debug;

import ui.Consola;
import modelo.Usuario;
import servicio.GestorUsuarios;

public class TestIntegrador {
    public static void main(String[] args) {
        Consola consola = new Consola();
        GestorUsuarios gestor = new GestorUsuarios();

        consola.mostrarMensaje("📚 Bienvenido al sistema de gestión de biblioteca");

        // Registrar 2 usuarios
        for (int i = 1; i <= 2; i++) {
            consola.mostrarMensaje("\n👤 Registro de Usuario " + i);
            String id = consola.leerEntrada("Ingrese ID");
            String nombre = consola.leerEntrada("Ingrese nombre");
            String email = consola.leerEntrada("Ingrese email");

            Usuario nuevo = new Usuario(id, nombre, email);
            gestor.registrarUsuario(nuevo);
        }

        consola.mostrarMensaje("\n✅ Usuarios registrados:");
        gestor.listarUsuarios();

        // Buscar uno
        consola.mostrarMensaje("\n🔍 Buscar un usuario por ID");
        String buscarId = consola.leerEntrada("Ingrese ID a buscar");
        Usuario encontrado = gestor.buscarPorId(buscarId);

        if (encontrado != null) {
            consola.mostrarMensaje("Usuario encontrado: " + encontrado);
        } else {
            consola.mostrarMensaje("❌ No se encontró el usuario.");
        }

        consola.cerrarScanner();
    }
}
