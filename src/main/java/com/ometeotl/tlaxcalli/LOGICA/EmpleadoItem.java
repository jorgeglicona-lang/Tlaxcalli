package com.ometeotl.tlaxcalli.LOGICA;

public class EmpleadoItem {
    private final int id;
    private final String nombre;

    public EmpleadoItem(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    
    @Override
    public String toString() { return nombre; }
}