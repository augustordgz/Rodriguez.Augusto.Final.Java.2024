package com.utn.finalprogramacion2.excepciones;

public class ProductoNoEncontradoExcepcion extends Exception {
    public ProductoNoEncontradoExcepcion(String codigo) {
        super("No se encontró algún producto con el código: " + codigo);
    }
}
