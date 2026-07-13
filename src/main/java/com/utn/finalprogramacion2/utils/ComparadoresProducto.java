package com.utn.finalprogramacion2.utils;

import com.utn.finalprogramacion2.models.Producto;
import java.util.Comparator;

public class ComparadoresProducto {

    // precio ascendente
    public static Comparator<Producto> porPrecio() {
        return Comparator.comparingDouble(Producto::getPrecio);
    }

    // stock descendente
    public static Comparator<Producto> porStockDescendente() {
        return Comparator.comparingInt(Producto::getStock).reversed();
    }

    // por código ascendente
    public static Comparator<Producto> porCodigo() {
        return Comparator.comparing(Producto::getCodigo);
    }
}