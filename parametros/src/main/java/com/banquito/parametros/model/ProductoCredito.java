package com.banquito.parametros.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "productos_credito", schema = "Parametros")
public class ProductoCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto_credito", nullable = false)
    private Integer idProductoCredito;

    @Column(name = "codigo_producto", length = 20, unique = true, nullable = false)
    private String codigoProducto;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 255, nullable = false)
    private String descripcion;

    @Column(name = "monto_minimo", precision = 12, scale = 2, nullable = false)
    private BigDecimal montoMinimo;

    @Column(name = "monto_maximo", precision = 12, scale = 2, nullable = false)
    private BigDecimal montoMaximo;

    @Column(name = "plazo_minimo_meses", nullable = false)
    private Integer plazoMinimoMeses;

    @Column(name = "plazo_maximo_meses", nullable = false)
    private Integer plazoMaximoMeses;

    @Column(name = "porcentaje_max_financiamiento", precision = 5, scale = 2, nullable = false)
    private BigDecimal porcentajeMaxFinanciamiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "condicion_vehiculo", nullable = false)
    private EstadosParametros.CondicionVehiculo condicionVehiculo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadosParametros.EstadoActivoInactivo estado;

    @Version
    @Column(name = "version", nullable = false, precision = 9)
    private Long version;

    public ProductoCredito() {
    }

    public ProductoCredito(Integer idProductoCredito) {
        this.idProductoCredito = idProductoCredito;
    }

    public Integer getIdProductoCredito() {
        return idProductoCredito;
    }

    public void setIdProductoCredito(Integer idProductoCredito) {
        this.idProductoCredito = idProductoCredito;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getMontoMinimo() {
        return montoMinimo;
    }

    public void setMontoMinimo(BigDecimal montoMinimo) {
        this.montoMinimo = montoMinimo;
    }

    public BigDecimal getMontoMaximo() {
        return montoMaximo;
    }

    public void setMontoMaximo(BigDecimal montoMaximo) {
        this.montoMaximo = montoMaximo;
    }

    public Integer getPlazoMinimoMeses() {
        return plazoMinimoMeses;
    }

    public void setPlazoMinimoMeses(Integer plazoMinimoMeses) {
        this.plazoMinimoMeses = plazoMinimoMeses;
    }

    public Integer getPlazoMaximoMeses() {
        return plazoMaximoMeses;
    }

    public void setPlazoMaximoMeses(Integer plazoMaximoMeses) {
        this.plazoMaximoMeses = plazoMaximoMeses;
    }

    public BigDecimal getPorcentajeMaxFinanciamiento() {
        return porcentajeMaxFinanciamiento;
    }

    public void setPorcentajeMaxFinanciamiento(BigDecimal porcentajeMaxFinanciamiento) {
        this.porcentajeMaxFinanciamiento = porcentajeMaxFinanciamiento;
    }

    public EstadosParametros.CondicionVehiculo getCondicionVehiculo() {
        return condicionVehiculo;
    }

    public void setCondicionVehiculo(EstadosParametros.CondicionVehiculo condicionVehiculo) {
        this.condicionVehiculo = condicionVehiculo;
    }

    public EstadosParametros.EstadoActivoInactivo getEstado() {
        return estado;
    }

    public void setEstado(EstadosParametros.EstadoActivoInactivo estado) {
        this.estado = estado;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idProductoCredito == null) ? 0 : idProductoCredito.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductoCredito other = (ProductoCredito) obj;
        if (idProductoCredito == null) {
            if (other.idProductoCredito != null)
                return false;
        } else if (!idProductoCredito.equals(other.idProductoCredito))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ProductoCredito [idProductoCredito=" + idProductoCredito + ", codigoProducto=" + codigoProducto
                + ", nombre=" + nombre + ", descripcion=" + descripcion + ", montoMinimo=" + montoMinimo
                + ", montoMaximo=" + montoMaximo + ", plazoMinimoMeses=" + plazoMinimoMeses + ", plazoMaximoMeses="
                + plazoMaximoMeses + ", porcentajeMaxFinanciamiento=" + porcentajeMaxFinanciamiento
                + ", condicionVehiculo=" + condicionVehiculo + ", estado=" + estado + ", version=" + version + "]";
    }
} 