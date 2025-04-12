package ui;

import java.util.Scanner;

public class Consola {
    private final Scanner scanner;

    public Consola() {
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public String leerEntrada(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    public int leerOpcionNumerica(String prompt) {
        System.out.print(prompt + ": ");
        while (!scanner.hasNextInt()) {
            System.out.println("⚠️ Entrada inválida. Ingrese un número.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public void cerrarScanner() {
        scanner.close();
    }
}
