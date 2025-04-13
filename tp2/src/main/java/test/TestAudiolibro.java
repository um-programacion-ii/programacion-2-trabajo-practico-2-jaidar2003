package test;

import recurso.Audiolibro;

public class TestAudiolibro {
    public static void main(String[] args) {
        Audiolibro a = new Audiolibro("A1", "1984", "Carlos Pérez");

        System.out.println(a);
        a.accederEnLinea();
        a.descargar();
    }
}
