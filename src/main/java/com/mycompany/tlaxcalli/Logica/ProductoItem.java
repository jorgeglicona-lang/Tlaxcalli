package com.mycompany.tlaxcalli.Logica;

public class ProductoItem {
    private int id;
    private String nombre;
    private double precio;
    private boolean esComodin;

    public ProductoItem(int id, String nombre, double precio, boolean esComodin) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.esComodin = esComodin;
    }

    // Getters
    public int getId() { return id; }
    public double getPrecio() { return precio; }
    public boolean isComodin() { return esComodin; }

    @Override
    public String toString() {
        return nombre; // Esto es lo que se verá en el ComboBox
    }
}