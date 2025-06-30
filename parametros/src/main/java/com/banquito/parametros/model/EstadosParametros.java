package com.banquito.parametros.model;

public class EstadosParametros {
    public enum EstadoActivoInactivo {
        ACTIVO,
        INACTIVO;

        public static EstadoActivoInactivo fromString(String valor) {
            if (valor == null) {
                return null;
            }
            try {
                return EstadoActivoInactivo.valueOf(valor.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Estado no válido: " + valor);
            }
        }
    }
    
    public enum CondicionVehiculo {
        NUEVO,
        SEMINUEVO,
        USADO;

        public static CondicionVehiculo fromString(String valor) {
            if (valor == null) {
                return null;
            }
            try {
                return CondicionVehiculo.valueOf(valor.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Condición de vehículo no válida: " + valor);
            }
        }
    }
} 