package com.mycompany.tlaxcalli.Logica;

public class C_NR {
    private int id;
    private String texto;

    public C_NR(int id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    public int getId() { return id; }
    
    @Override
    public String toString() { return texto; } // Esto es lo que se ve en el combo
}