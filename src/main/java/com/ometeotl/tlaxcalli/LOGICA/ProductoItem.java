package com.ometeotl.tlaxcalli.LOGICA;

public class ProductoItem {
    private final int id;
    private final String nombre;
    private final double precio;
    private final boolean esComodin;

    public ProductoItem(int id, String nombre, double precio, boolean esComodin) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.esComodin = esComodin;
    }

    public int getId() { return id; }
    public double getPrecio() { return precio; }
    public boolean isComodin() { return esComodin; }
    
    @Override
    public String toString() { return nombre; }
}