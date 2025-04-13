package test;

import modelo.Usuario;
import servicio.logica.GestorUsuarios;

public class TestUsuarios {
    public static void main(String[] args) {
        GestorUsuarios gestor = new GestorUsuarios();

        gestor.registrarUsuario(new Usuario("U1", "Ana", "ana@mail.com"));
        gestor.registrarUsuario(new Usuario("U2", "Pedro", "pedro@mail.com"));

        System.out.println("📋 Usuarios registrados:");
        gestor.listarUsuarios();
    }
}
