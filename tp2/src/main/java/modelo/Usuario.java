package modelo;

/**
 * Clase que representa a un usuario del sistema.
 */
public class Usuario {
    private final String id;
    private String nombre;
    private String email;
    private PreferenciasNotificacion preferencias;

    /**
     * Constructor del usuario.
     * @param id ID único del usuario
     * @param nombre Nombre completo
     * @param email Correo electrónico
     */
    public Usuario(String id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.preferencias = new PreferenciasNotificacion(); // Preferencias por defecto
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PreferenciasNotificacion getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(PreferenciasNotificacion preferencias) {
        this.preferencias = preferencias;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", preferencias=" + preferencias +
                '}';
    }
}
