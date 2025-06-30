package com.banquito.parametros.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Schema(description = "DTO para Tasa de Interés")
public class TasaInteresDTO {

    @Schema(description = "ID único de la tasa de interés", example = "1")
    private Integer idTasaInteres;

    @NotNull(message = "El ID del producto de crédito es requerido")
    @Min(value = 1, message = "El ID del producto de crédito debe ser mayor a 0")
    @Schema(description = "ID del producto de crédito asociado", example = "1", required = true)
    private Integer idProductoCredito;

    @NotBlank(message = "La base de cálculo es requerida")
    @Pattern(regexp = "^(360|365)\\s+días$", message = "La base de cálculo debe ser '360 días' o '365 días'")
    @Schema(description = "Base de cálculo para la tasa", example = "360 días", required = true)
    private String baseCalculo;

    @NotBlank(message = "El método de cálculo es requerido")
    @Pattern(regexp = "^(simple|compuesto)$", message = "El método de cálculo debe ser 'simple' o 'compuesto'")
    @Schema(description = "Método de cálculo de la tasa", example = "compuesto", required = true)
    private String metodoCalculo;

    @NotBlank(message = "La frecuencia de capitalización es requerida")
    @Pattern(regexp = "^(mensual|anual)$", message = "La frecuencia de capitalización debe ser 'mensual' o 'anual'")
    @Schema(description = "Frecuencia de capitalización", example = "mensual", required = true)
    private String frecuenciaCapitalizacion;

    @NotNull(message = "El valor de la tasa es requerido")
    @DecimalMin(value = "0.01", message = "El valor de la tasa debe ser mayor a 0")
    @DecimalMax(value = "50.00", message = "El valor de la tasa no puede exceder 50%")
    @Schema(description = "Valor de la tasa de interés en porcentaje", example = "12.75", required = true)
    private BigDecimal valorTasa;

    @NotNull(message = "La fecha de inicio de vigencia es requerida")
    @Schema(description = "Fecha de inicio de vigencia de la tasa", example = "2024-01-01", required = true)
    private LocalDate fechaInicioVigencia;

    @Schema(description = "Fecha de fin de vigencia de la tasa (opcional)", example = "2024-12-31")
    private LocalDate fechaFinVigencia;

    @NotBlank(message = "El estado de la tasa es requerido")
    @Pattern(regexp = "^(ACTIVO|INACTIVO)$", message = "El estado debe ser ACTIVO o INACTIVO")
    @Schema(description = "Estado de la tasa de interés", example = "ACTIVO", required = true)
    private String estado;
} 