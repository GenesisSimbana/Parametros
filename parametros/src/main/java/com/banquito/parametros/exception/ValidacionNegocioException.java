package com.banquito.parametros.exception;

public class ValidacionNegocioException extends RuntimeException {

    private final String campo;
    private final String entidad;

    public ValidacionNegocioException(String mensaje) {
        super(mensaje);
        this.campo = null;
        this.entidad = null;
    }

    public ValidacionNegocioException(String campo, String entidad, String mensaje) {
        super(mensaje);
        this.campo = campo;
        this.entidad = entidad;
    }

    public String getCampo() {
        return campo;
    }

    public String getEntidad() {
        return entidad;
    }
} 