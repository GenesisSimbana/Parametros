package com.banquito.parametros.controller.mapper;

import com.banquito.parametros.controller.dto.TasaInteresDTO;
import com.banquito.parametros.model.TasaInteres;
import com.banquito.parametros.model.EstadosParametros;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TasaInteresMapper {

    public TasaInteres toModel(TasaInteresDTO dto) {
        if (dto == null) {
            return null;
        }
        TasaInteres tasaInteres = new TasaInteres();
        tasaInteres.setIdTasaInteres(dto.getIdTasaInteres());
        tasaInteres.setIdProductoCredito(dto.getIdProductoCredito());
        tasaInteres.setBaseCalculo(dto.getBaseCalculo());
        tasaInteres.setMetodoCalculo(dto.getMetodoCalculo());
        tasaInteres.setFrecuenciaCapitalizacion(dto.getFrecuenciaCapitalizacion());
        tasaInteres.setValorTasa(dto.getValorTasa());
        tasaInteres.setFechaInicioVigencia(dto.getFechaInicioVigencia());
        tasaInteres.setFechaFinVigencia(dto.getFechaFinVigencia());
        if (dto.getEstado() != null) {
            try {
                tasaInteres.setEstado(EstadosParametros.EstadoActivoInactivo.valueOf(dto.getEstado()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Estado inválido: " + dto.getEstado());
            }
        }
        return tasaInteres;
    }

    public TasaInteresDTO toDTO(TasaInteres model) {
        if (model == null) {
            return null;
        }

        TasaInteresDTO tasaInteresDTO = new TasaInteresDTO();
        tasaInteresDTO.setIdTasaInteres(model.getIdTasaInteres());
        tasaInteresDTO.setIdProductoCredito(model.getIdProductoCredito());
        tasaInteresDTO.setBaseCalculo(model.getBaseCalculo());
        tasaInteresDTO.setMetodoCalculo(model.getMetodoCalculo());
        tasaInteresDTO.setFrecuenciaCapitalizacion(model.getFrecuenciaCapitalizacion());
        tasaInteresDTO.setValorTasa(model.getValorTasa());
        tasaInteresDTO.setFechaInicioVigencia(model.getFechaInicioVigencia());
        tasaInteresDTO.setFechaFinVigencia(model.getFechaFinVigencia());
        if (model.getEstado() != null) {
            tasaInteresDTO.setEstado(model.getEstado().name());
        }
        return tasaInteresDTO;
    }

    public List<TasaInteresDTO> toDTOList(List<TasaInteres> modelList) {
        if (modelList == null || modelList.isEmpty()) {
            return List.of();
        }
        return modelList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TasaInteres> toModelList(List<TasaInteresDTO> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            return List.of();
        }
        return dtoList.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
} 