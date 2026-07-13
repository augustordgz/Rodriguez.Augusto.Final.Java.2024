package com.utn.finalprogramacion2.models;

import java.io.Serializable;

import com.utn.finalprogramacion2.excepciones.StockInsuficienteExcepcion;

public abstract class Producto implements Comparable<Producto>, Serializable {

    private static final long serialVersionUID = 1L;

    protected String codigo;
    protected String nombre;
    protected double precio;
    protected int stock;
    protected Categoria categoria;

    public Producto(String codigo, String nombre, double precio, int stock, Categoria categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    public abstract double calcularPrecioFinal();

    public void aumentarStock(int cantidad) {
        if (cantidad > 0) {
            this.stock += cantidad;
        }
    }
    
    public void disminuirStock(int cantidad) throws StockInsuficienteExcepcion {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad no puede ser menor a 0");
        }

        if (cantidad > this.stock) {
            throw new StockInsuficienteExcepcion("No hay stock suficiente.");
        }

        this.stock -= cantidad;
    }
    
    @Override
    public int compareTo(Producto otro) {
        return this.nombre.compareToIgnoreCase(otro.nombre);
    }

    // Getters y setters
    
    public String getCodigo() {
        return codigo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public double getPrecio() {
        return precio;
    }
    
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public int getStock() {
        return stock;
    }
    
    public Categoria getCategoria() {
        return categoria;
    }
    
    @Override
    public String toString(){
        return String.format("[%s] %s - $%.2f - Stock: %d - Categoria: %s", codigo, nombre, precio, stock, categoria);
    }
}

