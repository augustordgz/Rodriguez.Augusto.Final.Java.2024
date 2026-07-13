package com.utn.finalprogramacion2.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.utn.finalprogramacion2.models.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSON {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static class ContenedorProductos {
        List<Consola> consolas = new ArrayList<>();
        List<Videojuego> videojuegos = new ArrayList<>();
        List<Accesorio> accesorios = new ArrayList<>();
    }

    public static void guardar(List<Producto> productos, String rutaArchivo) throws IOException {
        ContenedorProductos contenedor = new ContenedorProductos();
        for (Producto p : productos) {
            if (p instanceof Consola c) {
                contenedor.consolas.add(c);
            } else if (p instanceof Videojuego v) {
                contenedor.videojuegos.add(v);
            } else if (p instanceof Accesorio a) {
                contenedor.accesorios.add(a);
            }
        }

        try (Writer writer = new FileWriter(rutaArchivo)) {
            gson.toJson(contenedor, writer);
        }
    }

    public static List<Producto> cargar(String rutaArchivo) throws IOException {
        try (Reader reader = new FileReader(rutaArchivo)) {
            Type tipoContenedor = new TypeToken<ContenedorProductos>() {}.getType();
            ContenedorProductos contenedor = gson.fromJson(reader, tipoContenedor);

            List<Producto> productos = new ArrayList<>();
            productos.addAll(contenedor.consolas);
            productos.addAll(contenedor.videojuegos);
            productos.addAll(contenedor.accesorios);
            return productos;
        }
    }
}