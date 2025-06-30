package com.banquito.parametros.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tasas_interes", schema = "Parametros")
public class TasaInteres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tasa_interes", nullable = false)
    private Integer idTasaInteres;

    @Column(name = "id_producto_credito", nullable = false, insertable = false, updatable = false)
    private Integer idProductoCredito;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_producto_credito", nullable = false)
    private ProductoCredito productoCredito;

    @Column(name = "base_calculo", length = 15, nullable = false)
    private String baseCalculo;

    @Column(name = "metodo_calculo", length = 20, nullable = false)
    private String metodoCalculo;

    @Column(name = "frecuencia_capitalizacion", length = 15, nullable = false)
    private String frecuenciaCapitalizacion;

    @Column(name = "valor_tasa", precision = 5, scale = 2, nullable = false)
    private BigDecimal valorTasa;

    @Column(name = "fecha_inicio_vigencia", nullable = false)
    private LocalDate fechaInicioVigencia;

    @Column(name = "fecha_fin_vigencia")
    private LocalDate fechaFinVigencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadosParametros.EstadoActivoInactivo estado;

    @Version
    @Column(name = "version", nullable = false, precision = 9)
    private Long version;

    public TasaInteres() {
    }

    public TasaInteres(Integer idTasaInteres) {
        this.idTasaInteres = idTasaInteres;
    }

    public Integer getIdTasaInteres() {
        return idTasaInteres;
    }

    public void setIdTasaInteres(Integer idTasaInteres) {
        this.idTasaInteres = idTasaInteres;
    }

    public Integer getIdProductoCredito() {
        return idProductoCredito;
    }

    public void setIdProductoCredito(Integer idProductoCredito) {
        this.idProductoCredito = idProductoCredito;
    }

    public ProductoCredito getProductoCredito() {
        return productoCredito;
    }

    public void setProductoCredito(ProductoCredito productoCredito) {
        this.productoCredito = productoCredito;
    }

    public String getBaseCalculo() {
        return baseCalculo;
    }

    public void setBaseCalculo(String baseCalculo) {
        this.baseCalculo = baseCalculo;
    }

    public String getMetodoCalculo() {
        return metodoCalculo;
    }

    public void setMetodoCalculo(String metodoCalculo) {
        this.metodoCalculo = metodoCalculo;
    }

    public String getFrecuenciaCapitalizacion() {
        return frecuenciaCapitalizacion;
    }

    public void setFrecuenciaCapitalizacion(String frecuenciaCapitalizacion) {
        this.frecuenciaCapitalizacion = frecuenciaCapitalizacion;
    }

    public BigDecimal getValorTasa() {
        return valorTasa;
    }

    public void setValorTasa(BigDecimal valorTasa) {
        this.valorTasa = valorTasa;
    }

    public LocalDate getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    public void setFechaInicioVigencia(LocalDate fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    public LocalDate getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    public void setFechaFinVigencia(LocalDate fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia;
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
        result = prime * result + ((idTasaInteres == null) ? 0 : idTasaInteres.hashCode());
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
        TasaInteres other = (TasaInteres) obj;
        if (idTasaInteres == null) {
            if (other.idTasaInteres != null)
                return false;
        } else if (!idTasaInteres.equals(other.idTasaInteres))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TasaInteres [idTasaInteres=" + idTasaInteres + ", idProductoCredito=" + idProductoCredito
                + ", productoCredito=" + productoCredito + ", baseCalculo=" + baseCalculo + ", metodoCalculo="
                + metodoCalculo + ", frecuenciaCapitalizacion=" + frecuenciaCapitalizacion + ", valorTasa=" + valorTasa
                + ", fechaInicioVigencia=" + fechaInicioVigencia + ", fechaFinVigencia=" + fechaFinVigencia
                + ", estado=" + estado + ", version=" + version + "]";
    }
} 