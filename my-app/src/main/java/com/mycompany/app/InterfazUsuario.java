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
    