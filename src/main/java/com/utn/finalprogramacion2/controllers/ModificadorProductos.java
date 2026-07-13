package com.utn.finalprogramacion2.controllers;

import com.utn.finalprogramacion2.models.Producto;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.Function;

public class ModificadorProductos {
    public static void aplicarATodos(List<Producto> lista, Consumer<Producto> accion) {
        for (Producto p : lista) {
            accion.accept(p);
        }
    }

    public static void aplicarSiCumple(List<Producto> lista, Predicate<Producto> criterio, Consumer<Producto> accion) {
        for (Producto p : lista) {
            if (criterio.test(p)) {
                accion.accept(p);
            }
        }
    }

    public static <R> List<R> transformar(List<Producto> lista, Function<Producto, R> transformacion) {
        List<R> resultado = new java.util.ArrayList<>();
        for (Producto p : lista) {
            resultado.add(transformacion.apply(p));
        }
        return resultado;
    }
}