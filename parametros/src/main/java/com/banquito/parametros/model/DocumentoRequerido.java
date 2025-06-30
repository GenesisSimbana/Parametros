package com.banquito.parametros.model;

import jakarta.persistence.*;

@Entity
@Table(name = "documentos_requeridos", schema = "Parametros")
public class DocumentoRequerido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento_requerido", nullable = false)
    private Integer idDocumentoRequerido;

    @Column(name = "id_producto_credito", nullable = false, insertable = false, updatable = false)
    private Integer idProductoCredito;

    @ManyToOne()
    @JoinColumn(name = "id_producto_credito", nullable = false)
    private ProductoCredito productoCredito;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "extension", length = 10, nullable = false)
    private String extension;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadosParametros.EstadoActivoInactivo estado;

    @Version
    @Column(name = "version", nullable = false, precision = 9)
    private Long version;

    public DocumentoRequerido() {
    }

    public DocumentoRequerido(Integer idDocumentoRequerido) {
        this.idDocumentoRequerido = idDocumentoRequerido;
    }

    public Integer getIdDocumentoRequerido() {
        return idDocumentoRequerido;
    }

    public void setIdDocumentoRequerido(Integer idDocumentoRequerido) {
        this.idDocumentoRequerido = idDocumentoRequerido;
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

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
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
        result = prime * result + ((idDocumentoRequerido == null) ? 0 : idDocumentoRequerido.hashCode());
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
        DocumentoRequerido other = (DocumentoRequerido) obj;
        if (idDocumentoRequerido == null) {
            if (other.idDocumentoRequerido != null)
                return false;
        } else if (!idDocumentoRequerido.equals(other.idDocumentoRequerido))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DocumentoRequerido [idDocumentoRequerido=" + idDocumentoRequerido + ", idProductoCredito="
                + idProductoCredito + ", productoCredito=" + productoCredito + ", nombre=" + nombre + ", descripcion="
                + descripcion + ", extension=" + extension + ", estado=" + estado + ", version=" + version + "]";
    }
} 