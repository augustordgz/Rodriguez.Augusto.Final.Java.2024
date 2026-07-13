package com.utn.finalprogramacion2.utils;

import com.utn.finalprogramacion2.models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSV {

    private static final String SEPARADOR = ",";

    public static void guardar(List<Producto> productos, String rutaArchivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            bw.write("tipo,codigo,nombre,precio,stock,categoria,extra1,extra2");
            bw.newLine();

            for (Producto p : productos) {
                bw.write(convertirALinea(p));
                bw.newLine();
            }
        }
    }

    private static String convertirALinea(Producto p) {
        if (p instanceof Consola c) {
            return String.join(SEPARADOR, "CONSOLA", c.getCodigo(), c.getNombre(), String.valueOf(c.getPrecio()), String.valueOf(c.getStock()), c.getCategoria().name(),c.getMarca(), c.getAlmacenamiento());
        } else if (p instanceof Videojuego v) {
            return String.join(SEPARADOR, "VIDEOJUEGO", v.getCodigo(), v.getNombre(), String.valueOf(v.getPrecio()), String.valueOf(v.getStock()), v.getCategoria().name(), v.getGenero(), v.getPlataforma().name());
        } else if (p instanceof Accesorio a) {
            return String.join(SEPARADOR, "ACCESORIO", a.getCodigo(), a.getNombre(), String.valueOf(a.getPrecio()), String.valueOf(a.getStock()), a.getCategoria().name(), a.getTipo(), String.valueOf(a.isCompatibleMultiplataforma()));
        }
        return "";
    }

    public static List<Producto> cargar(String rutaArchivo) throws IOException {
        List<Producto> productos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea = br.readLine(); 
            while ((linea = br.readLine()) != null) {
                if (!linea.isBlank()) {
                    productos.add(convertirDesdeLinea(linea));
                }
            }
        }
        return productos;
    }

    private static Producto convertirDesdeLinea(String linea) {
        String[] campos = linea.split(SEPARADOR);
        String tipo = campos[0];
        String codigo = campos[1];
        String nombre = campos[2];
        double precio = Double.parseDouble(campos[3]);
        int stock = Integer.parseInt(campos[4]);
        Categoria categoria = Categoria.valueOf(campos[5]);

        switch (tipo) {
            case "CONSOLA":
                return new Consola(codigo, nombre, precio, stock, categoria, campos[6], campos[7]);
            case "VIDEOJUEGO":
                return new Videojuego(codigo, nombre, precio, stock, categoria, campos[6], Plataforma.valueOf(campos[7]));
            case "ACCESORIO":
                return new Accesorio(codigo, nombre, precio, stock, categoria, campos[6], Boolean.parseBoolean(campos[7]));
            default:
                throw new IllegalArgumentException("Tipo desconocido: " + tipo);
        }
    }
}