package com.banquito.parametros.service;

import com.banquito.parametros.controller.dto.*;
import com.banquito.parametros.controller.mapper.*;
import com.banquito.parametros.exception.*;
import com.banquito.parametros.model.*;
import com.banquito.parametros.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ParametrosService {

    private static final Logger log = LoggerFactory.getLogger(ParametrosService.class);
    
    private static final BigDecimal MONTO_MINIMO_VALIDO = new BigDecimal("1000.00");
    private static final BigDecimal TASA_MAXIMA_VALIDO = new BigDecimal("50.00");
    private static final BigDecimal PORCENTAJE_MIN_FINANCIAMIENTO = new BigDecimal("10.00");
    private static final BigDecimal PORCENTAJE_MAX_FINANCIAMIENTO = new BigDecimal("100.00");
    private static final int PLAZO_MAXIMO_MESES = 120;

    private final ProductoCreditoRepository productoCreditoRepository;
    private final TasaInteresRepository tasaInteresRepository;
    private final DocumentoRequeridoRepository documentoRequeridoRepository;
    private final ProductoCreditoMapper productoCreditoMapper;
    private final TasaInteresMapper tasaInteresMapper;
    private final DocumentoRequeridoMapper documentoRequeridoMapper;

    public ParametrosService(ProductoCreditoRepository productoCreditoRepository,
                           TasaInteresRepository tasaInteresRepository,
                           DocumentoRequeridoRepository documentoRequeridoRepository,
                           ProductoCreditoMapper productoCreditoMapper,
                           TasaInteresMapper tasaInteresMapper,
                           DocumentoRequeridoMapper documentoRequeridoMapper) {
        this.productoCreditoRepository = productoCreditoRepository;
        this.tasaInteresRepository = tasaInteresRepository;
        this.documentoRequeridoRepository = documentoRequeridoRepository;
        this.productoCreditoMapper = productoCreditoMapper;
        this.tasaInteresMapper = tasaInteresMapper;
        this.documentoRequeridoMapper = documentoRequeridoMapper;
    }

    // ========== PRODUCTOS DE CRÉDITO ==========

    public ProductoCreditoDTO crearProductoCredito(ProductoCreditoDTO dto) {
        log.info("Creando producto de crédito con código: {}", dto.getCodigoProducto());
        validarProductoCredito(dto);
        ProductoCredito producto = productoCreditoMapper.toModel(dto);
        ProductoCredito productoGuardado = productoCreditoRepository.save(producto);
        log.info("Producto de crédito creado exitosamente con ID: {}", productoGuardado.getIdProductoCredito());
        return productoCreditoMapper.toDTO(productoGuardado);
    }

    public ProductoCreditoDTO actualizarProductoCredito(Integer id, ProductoCreditoDTO dto) {
        log.info("Actualizando producto de crédito con ID: {}", id);
        ProductoCredito productoExistente = productoCreditoRepository.findById(id)
                .orElseThrow(() -> new ProductoCreditoNotFoundException(id));
        validarProductoCreditoActualizacion(id, dto);
        // Actualizar solo los campos editables de la entidad existente
        productoExistente.setCodigoProducto(dto.getCodigoProducto());
        productoExistente.setNombre(dto.getNombre());
        productoExistente.setDescripcion(dto.getDescripcion());
        productoExistente.setMontoMinimo(dto.getMontoMinimo());
        productoExistente.setMontoMaximo(dto.getMontoMaximo());
        productoExistente.setPlazoMinimoMeses(dto.getPlazoMinimoMeses());
        productoExistente.setPlazoMaximoMeses(dto.getPlazoMaximoMeses());
        productoExistente.setPorcentajeMaxFinanciamiento(dto.getPorcentajeMaxFinanciamiento());
        if (dto.getCondicionVehiculo() != null) {
            try {
                productoExistente.setCondicionVehiculo(EstadosParametros.CondicionVehiculo.valueOf(dto.getCondicionVehiculo()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Condición de vehículo inválida: " + dto.getCondicionVehiculo());
            }
        }
        
        if (dto.getEstado() != null) {
            try {
                productoExistente.setEstado(EstadosParametros.EstadoActivoInactivo.valueOf(dto.getEstado()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Estado inválido: " + dto.getEstado());
            }
        }
        
        ProductoCredito productoActualizado = productoCreditoRepository.save(productoExistente);
        log.info("Producto de crédito actualizado exitosamente");
        
        return productoCreditoMapper.toDTO(productoActualizado);
    }

    @Transactional(readOnly = true)
    public ProductoCreditoDTO obtenerProductoPorId(Integer id) {
        log.info("Obteniendo producto de crédito con ID: {}", id);
        ProductoCredito producto = productoCreditoRepository.findById(id)
                .orElseThrow(() -> new ProductoCreditoNotFoundException(id));
        return productoCreditoMapper.toDTO(producto);
    }

    @Transactional(readOnly = true)
    public ProductoCreditoDTO obtenerProductoPorCodigo(String codigo) {
        log.info("Obteniendo producto de crédito con código: {}", codigo);
        ProductoCredito producto = productoCreditoRepository.findByCodigoProducto(codigo)
                .orElseThrow(() -> new ProductoCreditoNotFoundException("Código: " + codigo));
        return productoCreditoMapper.toDTO(producto);
    }

    @Transactional(readOnly = true)
    public List<ProductoCreditoDTO> obtenerProductosActivos() {
        log.info("Obteniendo productos de crédito activos");
        List<ProductoCredito> productos = productoCreditoRepository
                .findByEstadoOrderByNombreAsc(EstadosParametros.EstadoActivoInactivo.ACTIVO);
        return productos.stream()
                .map(productoCreditoMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void validarProductoCredito(ProductoCreditoDTO dto) {
        validarMontosProducto(dto);
        validarPlazosProducto(dto);
        validarCodigoUnico(dto.getCodigoProducto());
    }

    private void validarProductoCreditoActualizacion(Integer id, ProductoCreditoDTO dto) {
        validarMontosProducto(dto);
        validarPlazosProducto(dto);
        validarCodigoUnicoActualizacion(id, dto.getCodigoProducto());
    }

    private void validarMontosProducto(ProductoCreditoDTO dto) {
        if (dto.getMontoMinimo().compareTo(MONTO_MINIMO_VALIDO) < 0) {
            throw new ValidacionNegocioException("montoMinimo", "ProductoCredito", 
                    "El monto mínimo debe ser al menos " + MONTO_MINIMO_VALIDO);
        }
        
        if (dto.getMontoMinimo().compareTo(dto.getMontoMaximo()) >= 0) {
            throw new ValidacionNegocioException("montoMinimo", "ProductoCredito", 
                    "El monto mínimo debe ser menor al monto máximo");
        }
        
        if (dto.getPorcentajeMaxFinanciamiento().compareTo(PORCENTAJE_MIN_FINANCIAMIENTO) < 0) {
            throw new ValidacionNegocioException("porcentajeMaxFinanciamiento", "ProductoCredito", 
                    "El porcentaje máximo de financiamiento debe ser al menos " + PORCENTAJE_MIN_FINANCIAMIENTO + "%");
        }
        
        if (dto.getPorcentajeMaxFinanciamiento().compareTo(PORCENTAJE_MAX_FINANCIAMIENTO) > 0) {
            throw new ValidacionNegocioException("porcentajeMaxFinanciamiento", "ProductoCredito", 
                    "El porcentaje máximo de financiamiento no puede exceder " + PORCENTAJE_MAX_FINANCIAMIENTO + "%");
        }
    }

    private void validarPlazosProducto(ProductoCreditoDTO dto) {
        if (dto.getPlazoMinimoMeses() < 1 || dto.getPlazoMaximoMeses() > PLAZO_MAXIMO_MESES) {
            throw new ValidacionNegocioException("plazoMinimoMeses", "ProductoCredito", 
                    "Los plazos deben estar entre 1 y " + PLAZO_MAXIMO_MESES + " meses");
        }
        
        if (dto.getPlazoMinimoMeses() >= dto.getPlazoMaximoMeses()) {
            throw new ValidacionNegocioException("plazoMinimoMeses", "ProductoCredito", 
                    "El plazo mínimo debe ser menor al plazo máximo");
        }
    }

    private void validarCodigoUnico(String codigo) {
        if (productoCreditoRepository.existsByCodigoProducto(codigo)) {
            throw new ValidacionNegocioException("codigoProducto", "ProductoCredito", 
                    "Ya existe un producto con el código: " + codigo);
        }
    }

    private void validarCodigoUnicoActualizacion(Integer id, String codigo) {
        Optional<ProductoCredito> productoExistente = productoCreditoRepository.findByCodigoProducto(codigo);
        if (productoExistente.isPresent() && !productoExistente.get().getIdProductoCredito().equals(id)) {
            throw new ValidacionNegocioException("codigoProducto", "ProductoCredito", 
                    "Ya existe otro producto con el código: " + codigo);
        }
    }

    // ========== TASAS DE INTERÉS ==========

    public TasaInteresDTO crearTasaInteres(TasaInteresDTO dto) {
        log.info("Creando tasa de interés para producto: {}", dto.getIdProductoCredito());
        validarTasaInteres(dto);        
        TasaInteres tasa = tasaInteresMapper.toModel(dto);
        ProductoCredito producto = productoCreditoRepository.findById(dto.getIdProductoCredito())
                .orElseThrow(() -> new ValidacionNegocioException("idProductoCredito", "TasaInteres", 
                        "El producto de crédito no existe"));
        tasa.setProductoCredito(producto);
        cerrarTasaAnterior(tasa);
        TasaInteres tasaGuardada = tasaInteresRepository.save(tasa);
        log.info("Tasa de interés creada exitosamente con ID: {}", tasaGuardada.getIdTasaInteres());
        
        return tasaInteresMapper.toDTO(tasaGuardada);
    }

    public TasaInteresDTO actualizarTasaInteres(Integer id, TasaInteresDTO dto) {
        log.info("Actualizando tasa de interés con ID: {}", id);
        TasaInteres tasaExistente = tasaInteresRepository.findById(id)
                .orElseThrow(() -> new TasaInteresNotFoundException(id, "Tasa de interés no encontrada"));
        
        validarTasaInteresActualizacion(id, dto);
        tasaExistente.setIdProductoCredito(dto.getIdProductoCredito());
        tasaExistente.setBaseCalculo(dto.getBaseCalculo());
        tasaExistente.setMetodoCalculo(dto.getMetodoCalculo());
        tasaExistente.setFrecuenciaCapitalizacion(dto.getFrecuenciaCapitalizacion());
        tasaExistente.setValorTasa(dto.getValorTasa());
        tasaExistente.setFechaInicioVigencia(dto.getFechaInicioVigencia());
        tasaExistente.setFechaFinVigencia(dto.getFechaFinVigencia());
        if (dto.getEstado() != null) {
            try {
                tasaExistente.setEstado(EstadosParametros.EstadoActivoInactivo.valueOf(dto.getEstado()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Estado inválido: " + dto.getEstado());
            }
        }
        
        ProductoCredito producto = productoCreditoRepository.findById(dto.getIdProductoCredito())
                .orElseThrow(() -> new ValidacionNegocioException("idProductoCredito", "TasaInteres", 
                        "El producto de crédito no existe"));
        tasaExistente.setProductoCredito(producto);
        
        TasaInteres tasaActualizada = tasaInteresRepository.save(tasaExistente);
        log.info("Tasa de interés actualizada exitosamente");
        
        return tasaInteresMapper.toDTO(tasaActualizada);
    }

    @Transactional(readOnly = true)
    public TasaInteresDTO obtenerTasaPorId(Integer id) {
        log.info("Obteniendo tasa de interés con ID: {}", id);
        TasaInteres tasa = tasaInteresRepository.findById(id)
                .orElseThrow(() -> new TasaInteresNotFoundException(id, "Tasa de interés no encontrada"));
        return tasaInteresMapper.toDTO(tasa);
    }

    @Transactional(readOnly = true)
    public List<TasaInteresDTO> obtenerTasasPorProducto(Integer idProducto) {
        log.info("Obteniendo tasas de interés para producto: {}", idProducto);
        
        List<TasaInteres> tasas = tasaInteresRepository
                .findByProductoCreditoOrderByFechaInicioVigenciaDesc(
                        productoCreditoRepository.getReferenceById(idProducto));
        return tasas.stream()
                .map(tasaInteresMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void validarTasaInteres(TasaInteresDTO dto) {
        validarProductoExiste(dto.getIdProductoCredito());
        validarFechasTasa(dto);
        validarValorTasa(dto);
        validarTraslapesTasa(dto);
    }

    private void validarTasaInteresActualizacion(Integer id, TasaInteresDTO dto) {
        validarProductoExiste(dto.getIdProductoCredito());
        validarFechasTasa(dto);
        validarValorTasa(dto);
        validarTraslapesTasaActualizacion(id, dto);
    }

    private void validarProductoExiste(Integer idProducto) {
        if (!productoCreditoRepository.existsById(idProducto)) {
            throw new ValidacionNegocioException("idProductoCredito", "TasaInteres", 
                    "El producto de crédito no existe");
        }
    }

    private void validarFechasTasa(TasaInteresDTO dto) {
        if (dto.getFechaInicioVigencia() == null) {
            throw new ValidacionNegocioException("fechaInicioVigencia", "TasaInteres", 
                    "La fecha de inicio de vigencia es requerida");
        }
        
        if (dto.getFechaFinVigencia() != null && 
            dto.getFechaInicioVigencia().isAfter(dto.getFechaFinVigencia())) {
            throw new ValidacionNegocioException("fechaInicioVigencia", "TasaInteres", 
                    "La fecha de inicio debe ser anterior a la fecha de fin");
        }
    }

    private void validarValorTasa(TasaInteresDTO dto) {
        if (dto.getValorTasa().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidacionNegocioException("valorTasa", "TasaInteres", 
                    "El valor de la tasa debe ser mayor a 0");
        }
        
        if (dto.getValorTasa().compareTo(TASA_MAXIMA_VALIDO) > 0) {
            throw new ValidacionNegocioException("valorTasa", "TasaInteres", 
                    "El valor de la tasa no puede exceder " + TASA_MAXIMA_VALIDO + "%");
        }
    }

    private void validarTraslapesTasa(TasaInteresDTO dto) {
        List<TasaInteres> tasasExistentes = tasaInteresRepository
                .findByProductoCreditoAndEstadoOrderByFechaInicioVigenciaDesc(
                        productoCreditoRepository.getReferenceById(dto.getIdProductoCredito()), 
                        EstadosParametros.EstadoActivoInactivo.ACTIVO);
        
        TasaInteres nuevaTasa = tasaInteresMapper.toModel(dto);
        for (TasaInteres tasaExistente : tasasExistentes) {
            if (hayTraslape(nuevaTasa, tasaExistente)) {
                throw new ValidacionNegocioException("fechaInicioVigencia", "TasaInteres", 
                        "Existe traslape con otra tasa vigente");
            }
        }
    }

    private void validarTraslapesTasaActualizacion(Integer id, TasaInteresDTO dto) {
        List<TasaInteres> tasasExistentes = tasaInteresRepository
                .findByProductoCreditoAndEstadoOrderByFechaInicioVigenciaDesc(
                        productoCreditoRepository.getReferenceById(dto.getIdProductoCredito()), 
                        EstadosParametros.EstadoActivoInactivo.ACTIVO);
        TasaInteres tasaActualizada = tasaInteresMapper.toModel(dto);
        tasaActualizada.setIdTasaInteres(id);
        for (TasaInteres tasaExistente : tasasExistentes) {
            if (!tasaExistente.getIdTasaInteres().equals(id) && hayTraslape(tasaActualizada, tasaExistente)) {
                throw new ValidacionNegocioException("fechaInicioVigencia", "TasaInteres", 
                        "Existe traslape con otra tasa vigente");
            }
        }
    }

    private boolean hayTraslape(TasaInteres tasa1, TasaInteres tasa2) {
        LocalDate inicio1 = tasa1.getFechaInicioVigencia();
        LocalDate fin1 = tasa1.getFechaFinVigencia();
        LocalDate inicio2 = tasa2.getFechaInicioVigencia();
        LocalDate fin2 = tasa2.getFechaFinVigencia();
        if (fin1 == null) fin1 = LocalDate.MAX;
        if (fin2 == null) fin2 = LocalDate.MAX;
        return !inicio1.isAfter(fin2) && !inicio2.isAfter(fin1);
    }

    private void cerrarTasaAnterior(TasaInteres nuevaTasa) {
        List<TasaInteres> tasasActivas = tasaInteresRepository
                .findByProductoCreditoAndEstadoOrderByFechaInicioVigenciaDesc(
                        nuevaTasa.getProductoCredito(), 
                        EstadosParametros.EstadoActivoInactivo.ACTIVO);
        
        for (TasaInteres tasaActiva : tasasActivas) {
            if (tasaActiva.getFechaFinVigencia() == null) {
                LocalDate fechaFin = nuevaTasa.getFechaInicioVigencia().minusDays(1);
                cerrarVigencia(tasaActiva, fechaFin);
                tasaInteresRepository.save(tasaActiva);
                log.info("Cerrada vigencia de tasa anterior con ID: {}", tasaActiva.getIdTasaInteres());
            }
        }
    }

    private void cerrarVigencia(TasaInteres tasa, LocalDate fechaFin) {
        tasa.setFechaFinVigencia(fechaFin);
    }

    // ========== DOCUMENTOS REQUERIDOS ==========

    public DocumentoRequeridoDTO crearDocumentoRequerido(DocumentoRequeridoDTO dto) {
        log.info("Creando documento requerido para producto: {}", dto.getIdProductoCredito());
        
        validarDocumentoRequerido(dto);
        
        DocumentoRequerido documento = documentoRequeridoMapper.toModel(dto);
        ProductoCredito producto = productoCreditoRepository.findById(dto.getIdProductoCredito())
                .orElseThrow(() -> new ValidacionNegocioException("idProductoCredito", "DocumentoRequerido", 
                        "El producto de crédito no existe"));
        documento.setProductoCredito(producto);
        
        DocumentoRequerido documentoGuardado = documentoRequeridoRepository.save(documento);
        log.info("Documento requerido creado exitosamente con ID: {}", documentoGuardado.getIdDocumentoRequerido());
        return documentoRequeridoMapper.toDTO(documentoGuardado);
    }

    public DocumentoRequeridoDTO actualizarDocumentoRequerido(Integer id, DocumentoRequeridoDTO dto) {
        log.info("Actualizando documento requerido con ID: {}", id);
        DocumentoRequerido documentoExistente = documentoRequeridoRepository.findById(id)
                .orElseThrow(() -> new DocumentoRequeridoNotFoundException(id));        
        validarDocumentoRequeridoActualizacion(id, dto);
        documentoExistente.setIdProductoCredito(dto.getIdProductoCredito());
        documentoExistente.setNombre(dto.getNombre());
        documentoExistente.setDescripcion(dto.getDescripcion());
        documentoExistente.setExtension(dto.getExtension());
        if (dto.getEstado() != null) {
            try {
                documentoExistente.setEstado(EstadosParametros.EstadoActivoInactivo.valueOf(dto.getEstado()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Estado inválido: " + dto.getEstado());
            }
        }
        ProductoCredito producto = productoCreditoRepository.findById(dto.getIdProductoCredito())
                .orElseThrow(() -> new ValidacionNegocioException("idProductoCredito", "DocumentoRequerido", 
                        "El producto de crédito no existe"));
        documentoExistente.setProductoCredito(producto);
        
        DocumentoRequerido documentoActualizado = documentoRequeridoRepository.save(documentoExistente);
        log.info("Documento requerido actualizado exitosamente");
        
        return documentoRequeridoMapper.toDTO(documentoActualizado);
    }

    @Transactional(readOnly = true)
    public DocumentoRequeridoDTO obtenerDocumentoPorId(Integer id) {
        log.info("Obteniendo documento requerido con ID: {}", id);
        
        DocumentoRequerido documento = documentoRequeridoRepository.findById(id)
                .orElseThrow(() -> new DocumentoRequeridoNotFoundException(id));
        
        return documentoRequeridoMapper.toDTO(documento);
    }

    @Transactional(readOnly = true)
    public List<DocumentoRequeridoDTO> obtenerDocumentosPorProducto(Integer idProducto) {
        log.info("Obteniendo documentos requeridos para producto: {}", idProducto);
        
        List<DocumentoRequerido> documentos = documentoRequeridoRepository
                .findByProductoCreditoAndEstadoOrderByNombreAsc(
                        productoCreditoRepository.getReferenceById(idProducto), 
                        EstadosParametros.EstadoActivoInactivo.ACTIVO);
        
        return documentos.stream()
                .map(documentoRequeridoMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void validarDocumentoRequerido(DocumentoRequeridoDTO dto) {
        validarProductoExiste(dto.getIdProductoCredito());
        validarNombreUnico(dto.getIdProductoCredito(), dto.getNombre());
    }

    private void validarDocumentoRequeridoActualizacion(Integer id, DocumentoRequeridoDTO dto) {
        validarProductoExiste(dto.getIdProductoCredito());
        validarNombreUnicoActualizacion(id, dto.getIdProductoCredito(), dto.getNombre());
    }

    private void validarNombreUnico(Integer idProducto, String nombre) {
        if (documentoRequeridoRepository.existsByProductoCreditoAndNombre(
                productoCreditoRepository.getReferenceById(idProducto), nombre)) {
            throw new ValidacionNegocioException("nombre", "DocumentoRequerido", 
                    "Ya existe un documento con el nombre: " + nombre + " para este producto");
        }
    }

    private void validarNombreUnicoActualizacion(Integer id, Integer idProducto, String nombre) {
        List<DocumentoRequerido> documentos = documentoRequeridoRepository
                .findByProductoCreditoOrderByNombreAsc(
                        productoCreditoRepository.getReferenceById(idProducto));
        for (DocumentoRequerido documento : documentos) {
            if (!documento.getIdDocumentoRequerido().equals(id) && 
                documento.getNombre().equals(nombre)) {
                throw new ValidacionNegocioException("nombre", "DocumentoRequerido", 
                        "Ya existe otro documento con el nombre: " + nombre + " para este producto");
            }
        }
    }
} 