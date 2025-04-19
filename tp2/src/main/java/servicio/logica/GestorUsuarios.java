package servicio.logica;

import modelo.Usuario;

import java.util.HashMap;
import java.util.Map;

public class GestorUsuarios {
    private final Map<String, Usuario> usuarios = new HashMap<>();

    public void registrarUsuario(Usuario usuario) {
        if (usuarios.containsKey(usuario.getId())) {
            System.out.println("❌ Ya existe un usuario con ese ID.");
        } else {
            usuarios.put(usuario.getId(), usuario);
            System.out.println("✅ Usuario registrado con éxito.");
        }
    }

    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("📭 No hay usuarios registrados.");
            return;
        }

        System.out.println("📋 Usuarios registrados:");
        for (Usuario usuario : usuarios.values()) {
            System.out.println(usuario);
        }
    }

    public Usuario buscarUsuarioPorId(String id) {
        return usuarios.get(id); // retorna null si no existe
    }

    public boolean existeUsuario(String id) {
        return usuarios.containsKey(id);
    }
}
