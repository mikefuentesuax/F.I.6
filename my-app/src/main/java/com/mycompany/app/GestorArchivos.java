package com.mycompany.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GestorArchivos {

    private List<File> indexedFiles = new ArrayList<>();

    public void indexDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            indexRecursive(directory);
        } else {
            System.out.println("El directorio no existe o no es válido.");
        }
    }

    private void indexRecursive(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    indexRecursive(f);
                }
            }
        } else {
            indexedFiles.add(file);
        }
    }

    public void listFilesSorted() {
        List<File> sortedFiles = new ArrayList<>(indexedFiles);
        Collections.sort(sortedFiles, Comparator.comparing(File::getName));

        for (File file : sortedFiles) {
            System.out.println(file.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        GestorArchivos gestorArchivos = new GestorArchivos();
        gestorArchivos.indexDirectory("/ruta/del/directorio"); // Reemplaza con la ruta del directorio que quieres indexar
        gestorArchivos.listFilesSorted();
    }
}
