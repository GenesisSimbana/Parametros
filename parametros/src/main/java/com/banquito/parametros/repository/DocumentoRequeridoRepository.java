package com.banquito.parametros.repository;

import com.banquito.parametros.model.DocumentoRequerido;
import com.banquito.parametros.model.EstadosParametros;
import com.banquito.parametros.model.ProductoCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRequeridoRepository extends JpaRepository<DocumentoRequerido, Integer> {

    List<DocumentoRequerido> findByProductoCreditoOrderByNombreAsc(ProductoCredito productoCredito);

    List<DocumentoRequerido> findByProductoCreditoAndEstadoOrderByNombreAsc(ProductoCredito productoCredito, EstadosParametros.EstadoActivoInactivo estado);

    List<DocumentoRequerido> findByEstadoOrderByProductoCreditoAscNombreAsc(EstadosParametros.EstadoActivoInactivo estado);

    boolean existsByProductoCreditoAndNombre(ProductoCredito productoCredito, String nombre);
} 