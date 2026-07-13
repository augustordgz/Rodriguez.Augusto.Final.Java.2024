package com.utn.finalprogramacion2.models;

public class Videojuego extends Producto implements Descontable {
    private String genero;
    private Plataforma plataforma;

    public Videojuego(String codigo, String nombre, double precio, int stock, Categoria categoria, String genero, Plataforma plataforma) {
        super(codigo, nombre, precio, stock, categoria);
        this.genero = genero;
        this.plataforma = plataforma;
    }

    // Sobrecarga con un parámetro menos 
    public Videojuego(String codigo, String nombre, double precio, int stock, Categoria categoria, Plataforma plataforma) {
        this(codigo, nombre, precio, stock, categoria, "Sin especificar", plataforma);
    }

    // Sobrecarga a elección 
    public Videojuego(String codigo, String nombre, double precio) {
        this(codigo, nombre, precio, 0, Categoria.SOFTWARE, "Sin especificar", Plataforma.PC);
    }

    @Override
    public double calcularPrecioFinal() {
        // Los juegos digitales tienen un 10% de descuento
        if (plataforma == Plataforma.PC) {
            return precio * 0.9;
        }
        return precio;
    }

    @Override
    public void aplicarDescuento(double porcentaje) {
        this.precio -= this.precio * (porcentaje / 100.0);
    }

    // Getters y setters
    public String getGenero() { 
        return genero; 
    }

    public Plataforma getPlataforma() { 
        return plataforma; 
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Genero: %s | Plataforma: %s", genero, plataforma);
    }
}