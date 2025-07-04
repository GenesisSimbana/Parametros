package com.banquito.parametros.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cliente-externo", url = "${app.cliente-externo.url:http://localhost:8081}")
public interface ClienteExternoClient {

    @GetMapping("/api/v1/clientes/{idCliente}")
    String obtenerInformacionCliente(@PathVariable("idCliente") String idCliente);
    @GetMapping("/api/v1/vehiculos/{vin}/validar")
    String validarVehiculo(@PathVariable("vin") String vin);
} 