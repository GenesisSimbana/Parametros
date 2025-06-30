package com.banquito.parametros.controller.mapper;

import com.banquito.parametros.controller.dto.ProductoCreditoDTO;
import com.banquito.parametros.model.ProductoCredito;
import com.banquito.parametros.model.EstadosParametros;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductoCreditoMapper {

    public ProductoCredito toModel(ProductoCreditoDTO dto) {
        if (dto == null) {
            return null;
        }

        ProductoCredito productoCredito = new ProductoCredito();
        productoCredito.setIdProductoCredito(dto.getIdProductoCredito());
        productoCredito.setCodigoProducto(dto.getCodigoProducto());
        productoCredito.setNombre(dto.getNombre());
        productoCredito.setDescripcion(dto.getDescripcion());
        productoCredito.setMontoMinimo(dto.getMontoMinimo());
        productoCredito.setMontoMaximo(dto.getMontoMaximo());
        productoCredito.setPlazoMinimoMeses(dto.getPlazoMinimoMeses());
        productoCredito.setPlazoMaximoMeses(dto.getPlazoMaximoMeses());
        productoCredito.setPorcentajeMaxFinanciamiento(dto.getPorcentajeMaxFinanciamiento());
        
        // Conversión segura del enum CondicionVehiculo
        if (dto.getCondicionVehiculo() != null) {
            try {
                productoCredito.setCondicionVehiculo(EstadosParametros.CondicionVehiculo.valueOf(dto.getCondicionVehiculo()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Condición de vehículo inválida: " + dto.getCondicionVehiculo());
            }
        }
        
        // Conversión segura del enum Estado
        if (dto.getEstado() != null) {
            try {
                productoCredito.setEstado(EstadosParametros.EstadoActivoInactivo.valueOf(dto.getEstado()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Estado inválida: " + dto.getEstado());
            }
        }
        
        return productoCredito;
    }

    public ProductoCreditoDTO toDTO(ProductoCredito model) {
        if (model == null) {
            return null;
        }

        ProductoCreditoDTO productoCreditoDTO = new ProductoCreditoDTO();
        productoCreditoDTO.setIdProductoCredito(model.getIdProductoCredito());
        productoCreditoDTO.setCodigoProducto(model.getCodigoProducto());
        productoCreditoDTO.setNombre(model.getNombre());
        productoCreditoDTO.setDescripcion(model.getDescripcion());
        productoCreditoDTO.setMontoMinimo(model.getMontoMinimo());
        productoCreditoDTO.setMontoMaximo(model.getMontoMaximo());
        productoCreditoDTO.setPlazoMinimoMeses(model.getPlazoMinimoMeses());
        productoCreditoDTO.setPlazoMaximoMeses(model.getPlazoMaximoMeses());
        productoCreditoDTO.setPorcentajeMaxFinanciamiento(model.getPorcentajeMaxFinanciamiento());
        
        // Conversión segura del enum CondicionVehiculo
        if (model.getCondicionVehiculo() != null) {
            productoCreditoDTO.setCondicionVehiculo(model.getCondicionVehiculo().name());
        }
        
        // Conversión segura del enum Estado
        if (model.getEstado() != null) {
            productoCreditoDTO.setEstado(model.getEstado().name());
        }

        return productoCreditoDTO;
    }

    public List<ProductoCreditoDTO> toDTOList(List<ProductoCredito> modelList) {
        if (modelList == null || modelList.isEmpty()) {
            return List.of();
        }

        return modelList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductoCredito> toModelList(List<ProductoCreditoDTO> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            return List.of();
        }

        return dtoList.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
} 