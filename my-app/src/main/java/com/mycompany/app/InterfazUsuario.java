package com.mycompany.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeSet;

public class InterfazUsuario extends JFrame {

    private DatosMultidimensionales<String> datosStrings;
    private TreeSet<String> nombresOrdenados;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaDatos;
    private JTextField campoDato;
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JComboBox<String> comboOrdenar;

    public InterfazUsuario() {
        datosStrings = new DatosMultidimensionales<>();
        nombresOrdenados = new TreeSet<>();
        modeloLista = new DefaultListModel<>();

        listaDatos = new JList<>(modeloLista);
        JScrollPane scrollLista = new JScrollPane(listaDatos);

        campoDato = new JTextField(20);

        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");

        comboOrdenar = new JComboBox<>(new String[]{"Sin orden", "Ordenar nombres", "Ordenar ventas"});
        comboOrdenar.addActionListener(e -> ordenarDatos());

        btnAgregar.addActionListener(e -> agregarDato());
        btnModificar.addActionListener(e -> modificarDato());
        btnEliminar.addActionListener(e -> eliminarDato());

        JPanel panelEntrada = new JPanel();
        panelEntrada.add(new JLabel("Dato:"));
        panelEntrada.add(campoDato);
        panelEntrada.add(btnAgregar);
        panelEntrada.add(btnModificar);
        panelEntrada.add(btnEliminar);
        panelEntrada.add(new JLabel("Ordenar por:"));
        panelEntrada.add(comboOrdenar);

        setLayout(new BorderLayout());
        add(scrollLista, BorderLayout.CENTER);
        add(panelEntrada, BorderLayout.SOUTH);

        setTitle("Interfaz de Usuario");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void agregarDato() {
        String dato = campoDato.getText();
        if (!dato.isEmpty()) {
            datosStrings.agregarDato(dato);
            nombresOrdenados.add(dato);
            actualizarLista();
            campoDato.setText("");
        }
    }

    private void modificarDato() {
        int indice = listaDatos.getSelectedIndex();
        if (indice != -1) {
            String datoAnterior = modeloLista.getElementAt(indice);
            String nuevoDato = campoDato.getText();
            if (!nuevoDato.isEmpty()) {
                datosStrings.eliminarDato(datoAnterior);
                nombresOrdenados.remove(datoAnterior);
                datosStrings.agregarDato(nuevoDato);
                nombresOrdenados.add(nuevoDato);
                actualizarLista();
                campoDato.setText("");
            }
        }
    }

    private void eliminarDato() {
        int indice = listaDatos.getSelectedIndex();
        if (indice != -1) {
            String dato = modeloLista.getElementAt(indice);
            datosStrings.eliminarDato(dato);
            nombresOrdenados.remove(dato);
            actualizarLista();
        }
    }

    private void ordenarDatos() {
        String criterio = (String) comboOrdenar.getSelectedItem();
        DefaultListModel<String> nuevaLista = new DefaultListModel<>();
        switch (criterio) {
            case "Ordenar nombres":
                for (String nombre : nombresOrdenados) {
                    nuevaLista.addElement(nombre);
                }
                break;
            case "Ordenar ventas":
                // Aquí puedes implementar la lógica para ordenar ventas
                // por diferentes criterios, por ejemplo, alfabéticamente o numéricamente.
                // Por ahora, simplemente mostraremos los datos sin ordenar.
                for (String dato : datosStrings.getDatos()) {
                    nuevaLista.addElement(dato);
                }
                break;
            default:
                for (String dato : datosStrings.getDatos()) {
                    nuevaLista.addElement(dato);
                }
                break;
        }
        listaDatos.setModel(nuevaLista);
    }

    private void actualizarLista() {
        DefaultListModel<String> nuevaLista = new DefaultListModel<>();
        for (String nombre : nombresOrdenados) {
            nuevaLista.addElement(nombre);
        }
        listaDatos.setModel(nuevaLista);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazUsuario());
    }
}
