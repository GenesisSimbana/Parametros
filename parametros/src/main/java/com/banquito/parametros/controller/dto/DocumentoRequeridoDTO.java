package com.banquito.parametros.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "DTO para Documento Requerido")
public class DocumentoRequeridoDTO {

    @Schema(description = "ID único del documento requerido", example = "1")
    private Integer idDocumentoRequerido;

    @NotNull(message = "El ID del producto de crédito es requerido")
    @Min(value = 1, message = "El ID del producto de crédito debe ser mayor a 0")
    @Schema(description = "ID del producto de crédito asociado", example = "1", required = true)
    private Integer idProductoCredito;

    @NotBlank(message = "El nombre del documento es requerido")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s\\d\\-\\.]+$", message = "El nombre contiene caracteres no permitidos")
    @Schema(description = "Nombre del documento requerido", example = "Cédula de Identidad", required = true)
    private String nombre;

    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres")
    @Schema(description = "Descripción detallada del documento", example = "Cédula de identidad vigente del solicitante")
    private String descripcion;

    @NotBlank(message = "La extensión del archivo es requerida")
    @Pattern(regexp = "^\\.(pdf|jpg|jpeg|png)$", message = "La extensión debe ser .pdf, .jpg, .jpeg o .png")
    @Schema(description = "Extensión permitida para el archivo", example = ".pdf", required = true)
    private String extension;

    @NotBlank(message = "El estado del documento es requerido")
    @Pattern(regexp = "^(ACTIVO|INACTIVO)$", message = "El estado debe ser ACTIVO o INACTIVO")
    @Schema(description = "Estado del documento requerido", example = "ACTIVO", required = true)
    private String estado;
} 