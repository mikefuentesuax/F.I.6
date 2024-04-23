package com.mycompany.app;
import java.util.*;

class DatosMultidimensionales<T> {
    private List<T> datos;

    public DatosMultidimensionales() {
        datos = new ArrayList<>();
    }

    public void agregarDato(T dato) {
        datos.add(dato);
    }

    public void eliminarDato(T dato) {
        datos.remove(dato);
    }

    public List<T> getDatos() {
        return datos;
    }

    public int cantidadDatos() {
        return datos.size();
    }

    public void limpiarDatos() {
        datos.clear();
    }
}
