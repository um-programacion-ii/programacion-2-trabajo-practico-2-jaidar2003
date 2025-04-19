package servicio.logica;

import excepciones.RecursoNoDisponibleException;
import modelo.Prestamo;
import modelo.Usuario;
import modelo.AlertaVencimiento;
import interfaz.Prestable;
import interfaz.RecursoDigital;
import interfaz.Renovable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestorPrestamos {
    private final List<Prestamo> prestamos = new ArrayList<>();

    public void registrarPrestamo(Usuario usuario, RecursoDigital recurso) throws RecursoNoDisponibleException {
        if (recurso instanceof Prestable prestable && prestable.estaDisponible()) {
            prestable.prestar(); // Cambiar el estado a PRESTADO
            Prestamo nuevoPrestamo = new Prestamo(usuario, recurso, LocalDate.now(), LocalDate.now().plusDays(7));
            prestamos.add(nuevoPrestamo);
            System.out.println("✅ Préstamo registrado exitosamente.");
        } else {
            throw new RecursoNoDisponibleException("El recurso no está disponible para préstamo.");
        }
    }

    public void listarPrestamos() {
        if (prestamos.isEmpty()) {
            System.out.println("📭 No hay préstamos registrados.");
        } else {
            prestamos.forEach(System.out::println);
        }
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void renovarPrestamo(Prestamo prestamo, int dias) {
        if (prestamo.getRecurso() instanceof Renovable) {
            prestamo.renovar(dias);
            System.out.println("✅ Préstamo renovado por " + dias + " días. Nueva fecha de vencimiento: " + prestamo.getFechaFin());
        } else {
            System.out.println("❌ Este recurso no es renovable.");
        }
    }

    public void verificarVencimientos() {
        verificarVencimientos(false);
    }

    public void verificarVencimientos(boolean modoTest) {
        System.out.println("🔍 Verificando vencimientos de préstamos...\n");

        boolean hayAlertas = false;
        List<Prestamo> prestamosConAlerta = new ArrayList<>();

        // Primero identificamos todos los préstamos con alertas
        for (Prestamo prestamo : prestamos) {
            AlertaVencimiento alerta = new AlertaVencimiento(prestamo);
            switch (alerta.getUrgencia()) {
                case WARNING, CRITICAL -> {
                    System.out.println(alerta.getMensaje());
                    prestamosConAlerta.add(prestamo);
                    hayAlertas = true;
                }
                default -> {} // No mostrar INFO
            }
        }

        // Si hay alertas y no estamos en modo test, ofrecemos la opción de renovar
        if (hayAlertas && !modoTest) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n¿Desea renovar algún préstamo? (S/N)");
            String respuesta = scanner.nextLine().trim().toUpperCase();

            if (respuesta.equals("S")) {
                for (int i = 0; i < prestamosConAlerta.size(); i++) {
                    Prestamo prestamo = prestamosConAlerta.get(i);
                    System.out.println((i + 1) + ". " + prestamo.getRecurso().getTitulo() + 
                                      " - Vence: " + prestamo.getFechaFin());
                }

                System.out.println("Ingrese el número del préstamo a renovar (o 0 para cancelar):");
                int seleccion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                if (seleccion > 0 && seleccion <= prestamosConAlerta.size()) {
                    Prestamo seleccionado = prestamosConAlerta.get(seleccion - 1);
                    System.out.println("¿Por cuántos días desea renovar el préstamo? (7/14/30):");
                    int dias = scanner.nextInt();
                    scanner.nextLine(); // Consumir el salto de línea

                    renovarPrestamo(seleccionado, dias);
                }
            }
        } else if (!hayAlertas) {
            System.out.println("✅ No hay préstamos próximos a vencer.");
        }
    }
}
