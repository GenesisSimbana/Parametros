package com.banquito.parametros.repository;

import com.banquito.parametros.model.EstadosParametros;
import com.banquito.parametros.model.ProductoCredito;
import com.banquito.parametros.model.TasaInteres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TasaInteresRepository extends JpaRepository<TasaInteres, Integer> {

    List<TasaInteres> findByProductoCreditoOrderByFechaInicioVigenciaDesc(ProductoCredito productoCredito);

    List<TasaInteres> findByProductoCreditoAndEstadoOrderByFechaInicioVigenciaDesc(
            ProductoCredito productoCredito, EstadosParametros.EstadoActivoInactivo estado);

    Optional<TasaInteres> findByProductoCreditoAndFechaInicioVigenciaLessThanEqualAndFechaFinVigenciaGreaterThanOrFechaFinVigenciaIsNullAndEstado(
            ProductoCredito productoCredito, LocalDate fecha, LocalDate fechaFin, EstadosParametros.EstadoActivoInactivo estado);

    List<TasaInteres> findByEstadoOrderByProductoCreditoAscFechaInicioVigenciaDesc(EstadosParametros.EstadoActivoInactivo estado);
} 