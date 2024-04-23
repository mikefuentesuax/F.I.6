package com.mycompany.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazUsuario extends JFrame {

    private DatosMultidimensionales<String> datosStrings;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaDatos;
    private JTextField campoDato;
    private JButton btnAgregar;
    private JButton btnEliminar;

    public InterfazUsuario() {
        datosStrings = new DatosMultidimensionales<>();
        modeloLista = new DefaultListModel<>();

        listaDatos = new JList<>(modeloLista);
        JScrollPane scrollLista = new JScrollPane(listaDatos);

        campoDato = new JTextField(20);

        btnAgregar = new JButton("Agregar");
        btnEliminar = new JButton("Eliminar");

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dato = campoDato.getText();
                if (!dato.isEmpty()) {
                    datosStrings.agregarDato(dato);
                    modeloLista.addElement(dato);
                    campoDato.setText("");
                }
            }
        });

        JPanel panelEntrada = new JPanel();
        panelEntrada.add(new JLabel("Dato:"));
        panelEntrada.add(campoDato);
        panelEntrada.add(btnAgregar);
        panelEntrada.add(btnEliminar);

        setLayout(new BorderLayout());
        add(scrollLista, BorderLayout.CENTER);
        add(panelEntrada, BorderLayout.SOUTH);

        setTitle("Interfaz de Usuario");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
