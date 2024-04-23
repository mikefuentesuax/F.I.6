package com.mycompany.app;

public class App {
    public static void main(String[] args) {
        // Crear una lista de datos multidimensionales de tipo entero
        DatosMultidimensionales<Integer> datosEnteros = new DatosMultidimensionales<>();
        datosEnteros.agregarDato(10);
        datosEnteros.agregarDato(20);
        datosEnteros.agregarDato(30);

        System.out.println("Datos enteros:");
        for (Integer dato : datosEnteros.getDatos()) {
            System.out.println(dato);
        }

        // Crear una lista de datos multidimensionales de tipo String
        DatosMultidimensionales<String> datosStrings = new DatosMultidimensionales<>();
        datosStrings.agregarDato("Hola");
        datosStrings.agregarDato("Mundo");

        System.out.println("\nDatos strings:");
        for (String dato : datosStrings.getDatos()) {
            System.out.println(dato);
        }

        // Crear una lista de pares enteros
        DatosMultidimensionales<Pareja<Integer, Integer>> datosPares = new DatosMultidimensionales<>();
        datosPares.agregarDato(new Pareja<>(1, 2));
        datosPares.agregarDato(new Pareja<>(3, 4));

        System.out.println("\nDatos pares enteros:");
        for (Pareja<Integer, Integer> pareja : datosPares.getDatos()) {
            System.out.println(pareja);
        }
    }
}