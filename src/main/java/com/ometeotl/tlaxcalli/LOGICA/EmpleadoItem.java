package com.ometeotl.tlaxcalli.LOGICA;

public class EmpleadoItem {
    private int id;
    private String nombre;

    public EmpleadoItem(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    
    @Override
    public String toString() {
        return nombre; // Esto se muestra en el ComboBox
    }
}