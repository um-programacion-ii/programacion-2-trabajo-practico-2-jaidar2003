package servicio.logica;

import modelo.Usuario;

import java.util.HashMap;
//HashMap es una implementación de la interfaz Map que almacena los elementos en pares clave-valor.
import java.util.Map;
//Map es una interfaz de Java que representa una colección clave → valor.

public class GestorUsuarios {
    private final Map<String, Usuario> usuarios;

    public GestorUsuarios() {
        this.usuarios = new HashMap<>();
    }

    public void registrarUsuario(Usuario usuario) {
        usuarios.put(usuario.getId(), usuario);
    }

    public Usuario buscarPorId(String id) {
        return usuarios.get(id);
    }

    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            System.out.println("Usuarios registrados:");
            for (Usuario u : usuarios.values()) {
                System.out.println(u);
            }
        }
    }

    public boolean existeUsuario(String id) {
        return usuarios.containsKey(id);
    }
}