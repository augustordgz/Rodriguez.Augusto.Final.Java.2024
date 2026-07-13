package com.utn.finalprogramacion2.controllers;

import com.utn.finalprogramacion2.models.Producto;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FiltroProductos {

    public static List<Producto> filtrar(List<? extends Producto> origen, Predicate<Producto> criterio) {
        List<Producto> resultado = new ArrayList<>();
        for (Producto p : origen) {
            if (criterio.test(p)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public static void agregarFiltrados(List<? extends Producto> origen, List<? super Producto> destino, Predicate<Producto> criterio) {
        for (Producto p : origen) {
            if (criterio.test(p)) {
                destino.add(p);
            }
        }
    }
}