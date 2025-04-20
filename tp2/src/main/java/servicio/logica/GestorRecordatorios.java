package servicio.logica;

import modelo.Recordatorio;
import modelo.Usuario;
import servicio.notificacion.NotificacionConsola;
import servicio.notificacion.ServicioNotificaciones;
import servicio.notificacion.ServicioNotificacionesEmail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Sistema que gestiona los recordatorios automáticos.
 * Permite crear, almacenar y enviar recordatorios a los usuarios según sus preferencias.
 */
public class GestorRecordatorios {
    private final List<Recordatorio> historialRecordatorios = new ArrayList<>();
    private final Map<Usuario, List<Recordatorio>> recordatoriosPorUsuario = new HashMap<>();
    private final ServicioNotificaciones servicioNotificacionesConsola;
    private final ServicioNotificacionesEmail servicioNotificacionesEmail;

    /**
     * Constructor que inicializa los servicios de notificación.
     */
    public GestorRecordatorios() {
        this.servicioNotificacionesConsola = new NotificacionConsola();
        this.servicioNotificacionesEmail = new ServicioNotificacionesEmail();
    }

    /**
     * Crea y envía un recordatorio a un usuario.
     * 
     * @param usuario Usuario destinatario
     * @param asunto Asunto del recordatorio
     * @param mensaje Mensaje del recordatorio
     * @param nivel Nivel de urgencia del recordatorio
     * @return El recordatorio creado
     */
    public Recordatorio crearRecordatorio(Usuario usuario, String asunto, String mensaje, Recordatorio.NivelUrgencia nivel) {
        Recordatorio recordatorio = new Recordatorio(usuario, asunto, mensaje, nivel);
        
        // Almacenar en el historial general
        historialRecordatorios.add(recordatorio);
        
        // Almacenar en la lista del usuario
        recordatoriosPorUsuario
            .computeIfAbsent(usuario, k -> new ArrayList<>())
            .add(recordatorio);
        
        // Enviar notificación según preferencias del usuario
        enviarNotificacion(recordatorio);
        
        return recordatorio;
    }

    /**
     * Envía una notificación al usuario según sus preferencias.
     * 
     * @param recordatorio Recordatorio a enviar
     */
    private void enviarNotificacion(Recordatorio recordatorio) {
        Usuario usuario = recordatorio.getUsuario();
        
        // Verificar si el usuario debe recibir este nivel de recordatorio
        if (!usuario.getPreferencias().debeRecibirPorNivel(recordatorio.getNivel())) {
            return;
        }
        
        // Enviar por consola si está habilitado
        if (usuario.getPreferencias().isRecibirPorConsola()) {
            servicioNotificacionesConsola.enviarNotificacion(recordatorio.getMensajeFormateado());
        }
        
        // Enviar por email si está habilitado
        if (usuario.getPreferencias().isRecibirPorEmail()) {
            servicioNotificacionesEmail.enviar(recordatorio.getMensajeFormateado());
        }
    }

    /**
     * Obtiene todos los recordatorios de un usuario.
     * 
     * @param usuario Usuario del que se quieren obtener los recordatorios
     * @return Lista de recordatorios del usuario
     */
    public List<Recordatorio> getRecordatoriosUsuario(Usuario usuario) {
        return recordatoriosPorUsuario.getOrDefault(usuario, new ArrayList<>());
    }

    /**
     * Obtiene los recordatorios no leídos de un usuario.
     * 
     * @param usuario Usuario del que se quieren obtener los recordatorios no leídos
     * @return Lista de recordatorios no leídos del usuario
     */
    public List<Recordatorio> getRecordatoriosNoLeidos(Usuario usuario) {
        return getRecordatoriosUsuario(usuario).stream()
                .filter(r -> !r.isLeido())
                .collect(Collectors.toList());
    }

    /**
     * Marca un recordatorio como leído.
     * 
     * @param recordatorio Recordatorio a marcar como leído
     */
    public void marcarComoLeido(Recordatorio recordatorio) {
        recordatorio.marcarComoLeido();
    }

    /**
     * Muestra el historial completo de recordatorios.
     */
    public void mostrarHistorialRecordatorios() {
        if (historialRecordatorios.isEmpty()) {
            System.out.println("📭 No hay recordatorios en el historial.");
            return;
        }

        System.out.println("📋 Historial de recordatorios:");
        for (int i = 0; i < historialRecordatorios.size(); i++) {
            Recordatorio r = historialRecordatorios.get(i);
            System.out.println((i + 1) + ". " + r.getMensajeFormateado() + 
                              " [" + r.getFechaCreacion() + "]" +
                              (r.isLeido() ? " [Leído]" : " [No leído]"));
        }
    }

    /**
     * Muestra los recordatorios de un usuario.
     * 
     * @param usuario Usuario del que se quieren mostrar los recordatorios
     */
    public void mostrarRecordatoriosUsuario(Usuario usuario) {
        List<Recordatorio> recordatorios = getRecordatoriosUsuario(usuario);
        
        if (recordatorios.isEmpty()) {
            System.out.println("📭 No hay recordatorios para " + usuario.getNombre());
            return;
        }

        System.out.println("📋 Recordatorios para " + usuario.getNombre() + ":");
        for (int i = 0; i < recordatorios.size(); i++) {
            Recordatorio r = recordatorios.get(i);
            System.out.println((i + 1) + ". " + r.getMensajeFormateado() + 
                              " [" + r.getFechaCreacion() + "]" +
                              (r.isLeido() ? " [Leído]" : " [No leído]"));
        }
    }

    /**
     * Cierra los servicios de notificación.
     */
    public void cerrar() {
        servicioNotificacionesEmail.cerrar();
    }
}