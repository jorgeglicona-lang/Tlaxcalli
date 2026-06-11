package com.ometeotl.tlaxcalli.LOGICA;

public class GastoItem {
    private final int id;
    private final String nombre;
    private final boolean requiereDescripcion;

    public GastoItem(int id, String nombre, boolean requiereDescripcion) {
        this.id = id;
        this.nombre = nombre;
        this.requiereDescripcion = requiereDescripcion;
    }

    public int getId() { return id; }
    public boolean isRequiereDescripcion() { return requiereDescripcion; }
    
    @Override
    public String toString() { return nombre; }
}