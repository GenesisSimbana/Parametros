package com.banquito.parametros.repository;

import com.banquito.parametros.model.EstadosParametros;
import com.banquito.parametros.model.ProductoCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoCreditoRepository extends JpaRepository<ProductoCredito, Integer> {

    Optional<ProductoCredito> findByCodigoProducto(String codigoProducto);

    List<ProductoCredito> findByEstado(EstadosParametros.EstadoActivoInactivo estado);

    boolean existsByCodigoProducto(String codigoProducto);

    List<ProductoCredito> findByEstadoOrderByNombreAsc(EstadosParametros.EstadoActivoInactivo estado);
} 