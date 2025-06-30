package com.banquito.parametros.exception;

public class TasaInteresNotFoundException extends RuntimeException {

    public TasaInteresNotFoundException(Integer id) {
        super("Tasa de interés no encontrada con ID: " + id);
    }

    public TasaInteresNotFoundException(String mensaje) {
        super("Tasa de interés no encontrada: " + mensaje);
    }

    public TasaInteresNotFoundException(Integer id, String mensaje) {
        super("Tasa de interés no encontrada con ID " + id + ": " + mensaje);
    }
} 