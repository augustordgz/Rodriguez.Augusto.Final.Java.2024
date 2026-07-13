package com.utn.finalprogramacion2.utils;

import java.io.*;
import java.util.List;

import com.utn.finalprogramacion2.models.Producto;

public class Serializacion {
    public static void guardar(List<Producto> productos, String rutaArchivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(productos);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Producto> cargar(String rutaArchivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (List<Producto>) ois.readObject();
        }
    }
}
