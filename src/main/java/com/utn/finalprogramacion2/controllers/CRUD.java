package com.utn.finalprogramacion2.controllers;

import java.util.List;

public interface CRUD<T> {
    void agregar(T elemento);
    boolean eliminar(String codigo);
    boolean actualizar(String codigo, T nuevoElemento);
    T buscarPorCodigo(String codigo);
    List<T> listarTodos();
}
