package com.banquito.parametros.exception;

public class ProductoCreditoNotFoundException extends RuntimeException {

    public ProductoCreditoNotFoundException(Integer id) {
        super("Producto de crédito no encontrado con ID: " + id);
    }

    public ProductoCreditoNotFoundException(String mensaje) {
        super("Producto de crédito no encontrado: " + mensaje);
    }

    public ProductoCreditoNotFoundException(Integer id, String mensaje) {
        super("Producto de crédito no encontrado con ID " + id + ": " + mensaje);
    }
} 