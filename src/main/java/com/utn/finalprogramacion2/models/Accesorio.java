package com.utn.finalprogramacion2.models;

public class Accesorio extends Producto {
    private String tipo; // ej: "Auricular", "Control", "Cable"
    private boolean compatibleMultiplataforma;

    public Accesorio(String codigo, String nombre, double precio, int stock, Categoria categoria, String tipo, boolean compatibleMultiplataforma) {
        super(codigo, nombre, precio, stock, categoria);
        this.tipo = tipo;
        this.compatibleMultiplataforma = compatibleMultiplataforma;
    }

    // Sobrecarga con un parámetro menos 
    public Accesorio(String codigo, String nombre, double precio, int stock, Categoria categoria, String tipo) {
        this(codigo, nombre, precio, stock, categoria, tipo, false);
    }

    // Sobrecarga a elección
    public Accesorio(String codigo, String nombre, double precio) {
        this(codigo, nombre, precio, 0, Categoria.PERIFERICO, "Generico", false);
    }

    @Override
    public double calcularPrecioFinal() {
        return precio;
    }

    // Getters y setters
    public String getTipo() { 
        return tipo; 
    }

    public boolean isCompatibleMultiplataforma() { 
        return compatibleMultiplataforma; 
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Tipo: %s | Multiplataforma: %s", tipo, compatibleMultiplataforma ? "Si" : "No");
    }
}