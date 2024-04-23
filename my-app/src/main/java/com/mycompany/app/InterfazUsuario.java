package com.mycompany.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InterfazUsuario extends JFrame {

    private DatosMultidimensionales<String> datosStrings;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaDatos;
    private JTextField campoDato;
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JTextField campoFiltro;

    public InterfazUsuario() {
        datosStrings = new DatosMultidimensionales<>();
        modeloLista = new DefaultListModel<>();

        listaDatos = new JList<>(modeloLista);
        JScrollPane scrollLista = new JScrollPane(listaDatos);

        campoDato = new JTextField(20);
        campoFiltro = new JTextField(20);

        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");

        btnAgregar.addActionListener(e -> agregarDato());
        btnModificar.addActionListener(e -> modificarDato());
        btnEliminar.addActionListener(e -> eliminarDato());

        campoFiltro.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarDatos();
            }
        });

        JPanel panelEntrada = new JPanel();
        panelEntrada.add(new JLabel("Dato:"));
        panelEntrada.add(campoDato);
        panelEntrada.add(btnAgregar);
        panelEntrada.add(btnModificar);
        panelEntrada.add(btnEliminar);
        panelEntrada.add(new JLabel("Filtro:"));
        panelEntrada.add(campoFiltro);

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
            modeloLista.addElement(dato);
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
                datosStrings.agregarDato(nuevoDato);
                modeloLista.setElementAt(nuevoDato, indice);
                campoDato.setText("");
            }
        }
    }

    private void eliminarDato() {
        int indice = listaDatos.getSelectedIndex();
        if (indice != -1) {
            String dato = modeloLista.getElementAt(indice);
            datosStrings.eliminarDato(dato);
            modeloLista.removeElementAt(indice);
        }
    }

    private void filtrarDatos() {
        String filtro = campoFiltro.getText().toLowerCase();
        DefaultListModel<String> nuevaLista = new DefaultListModel<>();
        for (int i = 0; i < modeloLista.getSize(); i++) {
            String dato = modeloLista.getElementAt(i);
            if (dato.toLowerCase().contains(filtro)) {
                nuevaLista.addElement(dato);
            }
        }
        listaDatos.setModel(nuevaLista);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazUsuario());
    }
}
