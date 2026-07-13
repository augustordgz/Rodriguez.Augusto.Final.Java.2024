package com.utn.finalprogramacion2.excepciones;

public class StockInsuficienteExcepcion extends Exception{
    public StockInsuficienteExcepcion(String mensaje) {
        super(mensaje);
    }

    public StockInsuficienteExcepcion(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
