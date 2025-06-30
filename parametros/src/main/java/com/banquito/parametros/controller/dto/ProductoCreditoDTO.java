package com.banquito.parametros.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Schema(description = "DTO para Producto de Crédito")
public class ProductoCreditoDTO {

    @Schema(description = "ID único del producto de crédito", example = "1")
    private Integer idProductoCredito;

    @NotBlank(message = "El código del producto es requerido")
    @Size(min = 3, max = 20, message = "El código debe tener entre 3 y 20 caracteres")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "El código debe contener solo letras mayúsculas y números")
    @Schema(description = "Código único del producto", example = "AUTO001", required = true)
    private String codigoProducto;

    @NotBlank(message = "El nombre del producto es requerido")
    @Size(min = 5, max = 100, message = "El nombre debe tener entre 5 y 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s\\d\\-\\.]+$", message = "El nombre contiene caracteres no permitidos")
    @Schema(description = "Nombre del producto de crédito", example = "Préstamo Automotriz Estándar", required = true)
    private String nombre;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    @Schema(description = "Descripción detallada del producto", example = "Préstamo para la compra de vehículos nuevos y usados")
    private String descripcion;

    @NotNull(message = "El monto mínimo es requerido")
    @DecimalMin(value = "1000.00", message = "El monto mínimo debe ser al menos $1,000")
    @Schema(description = "Monto mínimo del préstamo", example = "5000.00", required = true)
    private BigDecimal montoMinimo;

    @NotNull(message = "El monto máximo es requerido")
    @DecimalMin(value = "1000.00", message = "El monto máximo debe ser al menos $1,000")
    @Schema(description = "Monto máximo del préstamo", example = "50000.00", required = true)
    private BigDecimal montoMaximo;

    @NotNull(message = "El plazo mínimo es requerido")
    @Min(value = 1, message = "El plazo mínimo debe ser al menos 1 mes")
    @Max(value = 120, message = "El plazo mínimo no puede exceder 120 meses")
    @Schema(description = "Plazo mínimo en meses", example = "12", required = true)
    private Integer plazoMinimoMeses;

    @NotNull(message = "El plazo máximo es requerido")
    @Min(value = 1, message = "El plazo máximo debe ser al menos 1 mes")
    @Max(value = 120, message = "El plazo máximo no puede exceder 120 meses")
    @Schema(description = "Plazo máximo en meses", example = "60", required = true)
    private Integer plazoMaximoMeses;

    @NotNull(message = "El porcentaje máximo de financiamiento es requerido")
    @DecimalMin(value = "10.00", message = "El porcentaje máximo de financiamiento debe ser al menos 10%")
    @DecimalMax(value = "100.00", message = "El porcentaje máximo de financiamiento no puede exceder 100%")
    @Schema(description = "Porcentaje máximo de financiamiento del vehículo", example = "80.00", required = true)
    private BigDecimal porcentajeMaxFinanciamiento;

    @NotBlank(message = "La condición del vehículo es requerida")
    @Pattern(regexp = "^(NUEVO|USADO)$", message = "La condición del vehículo debe ser NUEVO o USADO")
    @Schema(description = "Condición del vehículo permitida", example = "NUEVO", required = true)
    private String condicionVehiculo;

    @NotBlank(message = "El estado del producto es requerido")
    @Pattern(regexp = "^(ACTIVO|INACTIVO)$", message = "El estado debe ser ACTIVO o INACTIVO")
    @Schema(description = "Estado del producto de crédito", example = "ACTIVO", required = true)
    private String estado;
} 