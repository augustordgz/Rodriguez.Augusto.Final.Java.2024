package com.utn.finalprogramacion2.controllers;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class IteradorProductos<T> implements Iterator<T> {
    private List<T> elementos;
    private int posicionActual;

    public IteradorProductos(List<T> elementos) {
        this.elementos = elementos;
        this.posicionActual = 0;
    }

    @Override
    public boolean hasNext() {
        return posicionActual < elementos.size();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No hay más elementos.");
        }
        T elemento = elementos.get(posicionActual);
        posicionActual++;
        return elemento;
    }
}
