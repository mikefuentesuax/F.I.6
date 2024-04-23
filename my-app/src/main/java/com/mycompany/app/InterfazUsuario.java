package com.mycompany.app;

import java.util.Map;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.Date;

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
    private Map<Integer, String> relacionNumerosLetras = new HashMap<>();
    private Map<String, List<TransaccionVenta>> transaccionesPorCliente = new HashMap<>();




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

        comboOrdenar = new JComboBox<>(new String[]{"Sin orden", "Ordenar nombres", "Ordenar ventas alfabéticamente", "Ordenar ventas numéricamente", "Búsqueda de cliente"});
        comboOrdenar.addActionListener(e -> ordenarYBuscar());

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
                transacciones.sort(Comparator.comparing(TransaccionVenta::getCliente));
                actualizarListaTransacciones();
                break;
            case "Ordenar ventas numéricamente":
                transacciones.sort(Comparator.comparing(TransaccionVenta::getMonto));
                actualizarListaTransacciones();
                break;
            case "Ordenar por fecha":
            transacciones.sort(Comparator.comparing(TransaccionVenta::getFecha, Comparator.nullsLast(Comparator.naturalOrder())));
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

    private void agregarRelacion(int numero, String letra) {
        relacionNumerosLetras.put(numero, letra);
    }

    private String obtenerLetra(int numero) {
        return relacionNumerosLetras.get(numero);
    }

    private void agregarTransaccionCliente(String cliente, TransaccionVenta transaccion) {
        transaccionesPorCliente.computeIfAbsent(cliente, k -> new ArrayList<>()).add(transaccion);
    }

    private List<TransaccionVenta> obtenerTransaccionesCliente(String cliente) {
        return transaccionesPorCliente.getOrDefault(cliente, new ArrayList<>());
    }

    private void ordenarYBuscar() {
        String criterio = (String) comboOrdenar.getSelectedItem();
        switch (criterio) {
            case "Ordenar nombres":
                actualizarListaOrdenada(nombresOrdenados);
                break;
            case "Ordenar ventas alfabéticamente":
                transacciones.sort(Comparator.comparing(TransaccionVenta::getCliente));
                actualizarListaTransacciones();
                break;
            case "Ordenar ventas numéricamente":
                transacciones.sort(Comparator.comparing(TransaccionVenta::getMonto));
                actualizarListaTransacciones();
                break;
            case "Búsqueda de cliente":
                String cliente = JOptionPane.showInputDialog("Ingrese el nombre del cliente a buscar:");
                if (cliente != null) {
                    buscarCliente(cliente);
                }
                break;
            default:
                break;
        }
    }

    private void buscarCliente(String cliente) {
        List<TransaccionVenta> resultados = transacciones.stream()
                .filter(t -> t.getCliente().equalsIgnoreCase(cliente))
                .collect(Collectors.toList());
        actualizarListaTransacciones(resultados);
    }

    private void actualizarLista() {
        DefaultListModel<String> nuevaLista = new DefaultListModel<>();
        for (String nombre : nombresOrdenados) {
            nuevaLista.addElement(nombre);
        }
        listaDatos.setModel(nuevaLista);
    }

    private void actualizarListaOrdenada(TreeSet<String> nombres) {
        modeloLista.clear();
        for (String nombre : nombres) {
            modeloLista.addElement(nombre);
        }
    }

    private void actualizarListaTransacciones() {
        modeloLista.clear();
        for (TransaccionVenta transaccion : transacciones) {
            modeloLista.addElement(transaccion.toString());
        }
    }

    private void actualizarListaTransacciones(List<TransaccionVenta> filtradas) {
        modeloLista.clear();
        for (TransaccionVenta transaccion : filtradas) {
            modeloLista.addElement(transaccion.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazUsuario());
    }
}

class TransaccionVenta {
    private String cliente;
    private double monto;
    private Date fecha;

    public TransaccionVenta(String cliente, double monto) {
        this.cliente = cliente;
        this.monto = monto;
        this.fecha = new Date(); // Fecha actual como ejemplo
    }

    public String getCliente() {
        return cliente;
    }

    public double getMonto() {
        return monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public String toString() {
        return "Cliente: " + cliente + ", Monto: $" + monto + ", Fecha: " + fecha.toString();
    }
}
