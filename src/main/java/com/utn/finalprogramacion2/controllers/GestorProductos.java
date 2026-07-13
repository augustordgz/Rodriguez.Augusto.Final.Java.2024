package com.utn.finalprogramacion2.controllers;

import com.utn.finalprogramacion2.excepciones.ProductoNoEncontradoExcepcion;
import com.utn.finalprogramacion2.models.Producto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestorProductos implements CRUD<Producto>, Iterable<Producto> {
    private List<Producto> productos;

    public GestorProductos() {
        this.productos = new ArrayList<>();
    }

    @Override
    public void agregar(Producto elemento) {
        productos.add(elemento);
    }

    @Override
    public boolean eliminar(String codigo) {
        return productos.removeIf(p -> p.getCodigo().equals(codigo));
    }

    @Override
    public boolean actualizar(String codigo, Producto nuevoElemento) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo().equals(codigo)) {
                productos.set(i, nuevoElemento);
                return true;
            }
        }
        return false;
    }

    @Override
    public Producto buscarPorCodigo(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    public Producto buscarOFallar(String codigo) throws ProductoNoEncontradoExcepcion {
        Producto encontrado = buscarPorCodigo(codigo);

        if (encontrado == null) {
            throw new ProductoNoEncontradoExcepcion(codigo);
        }

        return encontrado;
    }

    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos); 
    }

    @Override
    public Iterator<Producto> iterator() {
        return new IteradorProductos<>(productos);
    }

    public int cantidad() {
        return productos.size();
    }
}