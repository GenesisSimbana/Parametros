package com.banquito.parametros.controller.mapper;

import com.banquito.parametros.controller.dto.DocumentoRequeridoDTO;
import com.banquito.parametros.model.DocumentoRequerido;
import com.banquito.parametros.model.EstadosParametros;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DocumentoRequeridoMapper {

    public DocumentoRequerido toModel(DocumentoRequeridoDTO dto) {
        if (dto == null) {
            return null;
        }
        DocumentoRequerido documentoRequerido = new DocumentoRequerido();
        documentoRequerido.setIdDocumentoRequerido(dto.getIdDocumentoRequerido());
        documentoRequerido.setIdProductoCredito(dto.getIdProductoCredito());
        documentoRequerido.setNombre(dto.getNombre());
        documentoRequerido.setDescripcion(dto.getDescripcion());
        documentoRequerido.setExtension(dto.getExtension());
        if (dto.getEstado() != null) {
            try {
                documentoRequerido.setEstado(EstadosParametros.EstadoActivoInactivo.valueOf(dto.getEstado()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Estado inválido: " + dto.getEstado());
            }
        }
        return documentoRequerido;
    }

    public DocumentoRequeridoDTO toDTO(DocumentoRequerido model) {
        if (model == null) {
            return null;
        }

        DocumentoRequeridoDTO documentoRequeridoDTO = new DocumentoRequeridoDTO();
        documentoRequeridoDTO.setIdDocumentoRequerido(model.getIdDocumentoRequerido());
        documentoRequeridoDTO.setIdProductoCredito(model.getIdProductoCredito());
        documentoRequeridoDTO.setNombre(model.getNombre());
        documentoRequeridoDTO.setDescripcion(model.getDescripcion());
        documentoRequeridoDTO.setExtension(model.getExtension());
        
        if (model.getEstado() != null) {
            documentoRequeridoDTO.setEstado(model.getEstado().name());
        }

        return documentoRequeridoDTO;
    }

    public List<DocumentoRequeridoDTO> toDTOList(List<DocumentoRequerido> modelList) {
        if (modelList == null || modelList.isEmpty()) {
            return List.of();
        }

        return modelList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<DocumentoRequerido> toModelList(List<DocumentoRequeridoDTO> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            return List.of();
        }

        return dtoList.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
} 