package com.utn.finalprogramacion2.utils;
import com.utn.finalprogramacion2.models.Producto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Texto {

    public static void exportar(List<Producto> productos, String rutaArchivo, String tituloFiltro) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            bw.write("-------------------------------------------------");
            bw.newLine();
            bw.write(" LISTADO DE PRODUCTOS ");
            bw.newLine();
            bw.write(" Filtro aplicado: " + tituloFiltro);
            bw.newLine();
            bw.write(" Total de productos: " + productos.size());
            bw.newLine();
            bw.write("-------------------------------------------------");
            bw.newLine();
            bw.newLine();

            int contador = 1;
            for (Producto p : productos) {
                bw.write(contador + ". " + p.toString());
                bw.newLine();
                contador++;
            }

            if (productos.isEmpty()) {
                bw.write("No hay productos que coincidan con el filtro aplicado.");
                bw.newLine();
            }
        }
    }
}