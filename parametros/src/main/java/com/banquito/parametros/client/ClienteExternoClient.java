package com.banquito.parametros.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign de ejemplo para consumir servicios externos
 * Este es un ejemplo de cómo se implementaría la comunicación con otros microservicios
 */
@FeignClient(name = "cliente-externo", url = "${app.cliente-externo.url:http://localhost:8081}")
public interface ClienteExternoClient {

    /**
     * Ejemplo de método para obtener información de un cliente
     * @param idCliente ID del cliente
     * @return Información del cliente
     */
    @GetMapping("/api/v1/clientes/{idCliente}")
    String obtenerInformacionCliente(@PathVariable("idCliente") String idCliente);

    /**
     * Ejemplo de método para validar un vehículo
     * @param vin Número de identificación del vehículo
     * @return Información de validación del vehículo
     */
    @GetMapping("/api/v1/vehiculos/{vin}/validar")
    String validarVehiculo(@PathVariable("vin") String vin);
} 