package com.banquito.parametros.exception;

public class DocumentoRequeridoNotFoundException extends RuntimeException {

    public DocumentoRequeridoNotFoundException(Integer id) {
        super("Documento requerido no encontrado con ID: " + id);
    }

    public DocumentoRequeridoNotFoundException(String mensaje) {
        super("Documento requerido no encontrado: " + mensaje);
    }

    public DocumentoRequeridoNotFoundException(Integer id, String mensaje) {
        super("Documento requerido no encontrado con ID " + id + ": " + mensaje);
    }
} 