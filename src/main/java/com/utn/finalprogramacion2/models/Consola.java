package com.utn.finalprogramacion2.models;

public class Consola extends Producto implements Descontable {
    private String marca;
    private String almacenamiento;

    public Consola(String codigo, String nombre, double precio, int stock, Categoria categoria, String marca, String almacenamiento) {
        super(codigo, nombre, precio, stock, categoria);
        this.marca = marca;
        this.almacenamiento = almacenamiento;
    }

    // sobrecarga con un parámetro menos
    public Consola(String codigo, String nombre, double precio, int stock, Categoria categoria, String marca) {
        this(codigo, nombre, precio, stock, categoria, marca, "No especificado");
    }

    // sobrecarga a elección
    public Consola(String codigo, String nombre, double precio) {
        this(codigo, nombre, precio, 0, Categoria.HARDWARE, "Generica", "No especificado");
    }

    @Override
    public double calcularPrecioFinal() {
        return precio; 
    }

    @Override
    public void aplicarDescuento(double porcentaje) {
        this.precio -= this.precio * (porcentaje / 100.0);
    }

    // Getters y setters
    public String getMarca() { 
        return marca; 
    }

    public String getAlmacenamiento() { 
        return almacenamiento; 
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Marca: %s | Almacenamiento: %s", marca, almacenamiento);
    }
}