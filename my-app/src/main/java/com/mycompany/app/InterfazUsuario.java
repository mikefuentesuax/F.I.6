package com.mycompany.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<TransaccionVenta> transacciones;

    public InterfazUsuario() {
        datosStrings = new DatosMultidimensionales<>();
        nombresOrdenados = new TreeSet<>();
        modeloLista = new DefaultListModel<>();
        transacciones = new ArrayList<>();

        listaDatos = new JList<>(modeloLista);
        JScrollPane scrollLista = new JScrollPane(listaDatos);

        campoDato = new JTextField(20);

        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");

        comboOrdenar = new JComboBox<>(new String[]{"Sin orden", "Ordenar nombres", "Ordenar ventas alfabéticamente", "Ordenar ventas numéricamente"});
        comboOrdenar.addActionListener(e -> ordenarDatos());

        JButton btnAgregarTransaccion = new JButton("Agregar Transacción");
        btnAgregarTransaccion.addActionListener(e -> agregarTransaccion());

        JComboBox<String> comboCriterio = new JComboBox<>(new String[]{"Sin orden", "Ordenar por fecha", "Ordenar por monto", "Filtrar por cliente"});
        comboCriterio.addActionListener(e -> ordenarYFiltrarTransacciones());

        JPanel panelAcciones = new JPanel();
        panelAcciones.add(btnAgregar);
        panelAcciones.add(btnModificar);
        panelAcciones.add(btnEliminar);
        panelAcciones.add(comboOrdenar);
        panelAcciones.add(btnAgregarTransaccion);
        panelAcciones.add(comboCriterio);


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
        add(panelAcciones, BorderLayout.NORTH);
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

    private void agregarTransaccion() {
        String cliente = JOptionPane.showInputDialog("Ingrese el nombre del cliente:");
        double monto = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto de la venta:"));
        TransaccionVenta transaccion = new TransaccionVenta(cliente, monto);
        transacciones.add(transaccion);
        actualizarListaTransacciones();
    }

    private void ordenarYFiltrarTransacciones() {
        String criterio = (String) comboOrdenar.getSelectedItem();
        DefaultListModel<String> nuevaLista = new DefaultListModel<>();
        switch (criterio) {
            case "Ordenar nombres":
                for (String nombre : nombresOrdenados) {
                    nuevaLista.addElement(nombre);
                }
                break;
            case "Ordenar ventas alfabéticamente":
                ordenarAlfabeticamente(nuevaLista);
                break;
            case "Ordenar ventas numéricamente":
                ordenarNumericamente(nuevaLista);
                break;
            case "Ordenar por fecha":
                transacciones.sort(Comparator.comparing(TransaccionVenta::getFecha));
                actualizarListaTransacciones();
                break;
            case "Ordenar por monto":
                transacciones.sort(Comparator.comparing(TransaccionVenta::getMonto));
                actualizarListaTransacciones();
                break;
            case "Filtrar por cliente":
                String cliente = JOptionPane.showInputDialog("Ingrese el nombre del cliente para filtrar:");
                List<TransaccionVenta> filtradas = transacciones.stream()
                        .filter(t -> t.getCliente().equalsIgnoreCase(cliente))
                        .collect(Collectors.toList());
                actualizarListaTransacciones(filtradas);
                break;
            default:
                actualizarListaTransacciones();
                break;
        }
    }
    private void actualizarLista() {
        DefaultListModel<String> nuevaLista = new DefaultListModel<>();
        for (String nombre : nombresOrdenados) {
            nuevaLista.addElement(nombre);
        }
        listaDatos.setModel(nuevaLista);
    }

    private void actualizarListaTransacciones() {
        DefaultListModel<String> nuevaLista = new DefaultListModel<>();
        for (TransaccionVenta transaccion : transacciones) {
            nuevaLista.addElement(transaccion.toString());
        }
        listaDatos.setModel(nuevaLista);
    }

    private void actualizarListaTransacciones(List<TransaccionVenta> filtradas) {
        DefaultListModel<String> nuevaLista = new DefaultListModel<>();
        for (TransaccionVenta transaccion : filtradas) {
            nuevaLista.addElement(transaccion.toString());
        }
        listaDatos.setModel(nuevaLista);
    }

    private void ordenarAlfabeticamente(DefaultListModel<String> modelo) {
        TreeSet<String> set = new TreeSet<>();
        for (int i = 0; i < modelo.getSize(); i++) {
            set.add(modelo.getElementAt(i));
        }
        modelo.clear();
        for (String elemento : set) {
            modelo.addElement(elemento);
        }
    }

    private void ordenarNumericamente(DefaultListModel<String> modelo) {
        TreeSet<String> set = new TreeSet<>((s1, s2) -> {
            int num1 = Integer.parseInt(s1);
            int num2 = Integer.parseInt(s2);
            return Integer.compare(num1, num2);
        });
        for (int i = 0; i < modelo.getSize(); i++) {
            set.add(modelo.getElementAt(i));
        }
        modelo.clear();
        for (String elemento : set) {
            modelo.addElement(elemento);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazUsuario());
    }
}

class TransaccionVenta {
    private String cliente;
    private double monto;

    public TransaccionVenta(String cliente, double monto) {
        this.cliente = cliente;
        this.monto = monto;
    }

    public String getCliente() {
        return cliente;
    }

    public double getMonto() {
        return monto;
    }

    public String toString() {
        return "Cliente: " + cliente + ", Monto: $" + monto;
    }
}
