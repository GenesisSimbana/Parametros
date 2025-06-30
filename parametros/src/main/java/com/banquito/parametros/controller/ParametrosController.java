package com.banquito.parametros.controller;

import com.banquito.parametros.controller.dto.*;;
import com.banquito.parametros.exception.*;
import com.banquito.parametros.service.ParametrosService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parametros")
public class ParametrosController {

    private static final Logger log = LoggerFactory.getLogger(ParametrosController.class);
    
    private final ParametrosService parametrosService;

    public ParametrosController(ParametrosService parametrosService) {
        this.parametrosService = parametrosService;
    }

    // ========== PRODUCTOS DE CRÉDITO ==========

    @PostMapping("/productos-credito")
    public ResponseEntity<ProductoCreditoDTO> crearProductoCredito(
            @Valid @RequestBody ProductoCreditoDTO productoCreditoDTO) {
        log.info("Recibida solicitud para crear producto de crédito: {}", productoCreditoDTO.getCodigoProducto());
        ProductoCreditoDTO productoCreado = parametrosService.crearProductoCredito(productoCreditoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
    }

    @PutMapping("/productos-credito/{id}")
    public ResponseEntity<ProductoCreditoDTO> actualizarProductoCredito(
            @PathVariable Integer id,
            @Valid @RequestBody ProductoCreditoDTO productoCreditoDTO) {
        log.info("Recibida solicitud para actualizar producto de crédito con ID: {}", id);
        ProductoCreditoDTO productoActualizado = parametrosService.actualizarProductoCredito(id, productoCreditoDTO);
        return ResponseEntity.ok(productoActualizado);
    }

    @GetMapping("/productos-credito/codigo/{codigo}")
    public ResponseEntity<ProductoCreditoDTO> obtenerProductoPorCodigo(@PathVariable String codigo) {
        log.info("Recibida solicitud para obtener producto de crédito con código: {}", codigo);
        
        ProductoCreditoDTO producto = parametrosService.obtenerProductoPorCodigo(codigo);
        
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/productos-credito/activos")
    public ResponseEntity<List<ProductoCreditoDTO>> obtenerProductosActivos() {
        log.info("Recibida solicitud para obtener productos de crédito activos");
        
        List<ProductoCreditoDTO> productos = parametrosService.obtenerProductosActivos();
        
        return ResponseEntity.ok(productos);
    }

    // ========== TASAS DE INTERÉS ==========

    @PostMapping("/tasas-interes")
    public ResponseEntity<TasaInteresDTO> crearTasaInteres(
            @Valid @RequestBody TasaInteresDTO tasaInteresDTO) {
        log.info("Recibida solicitud para crear tasa de interés para producto: {}", tasaInteresDTO.getIdProductoCredito());
        
        TasaInteresDTO tasaCreada = parametrosService.crearTasaInteres(tasaInteresDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(tasaCreada);
    }

    @PutMapping("/tasas-interes/{id}")
    public ResponseEntity<TasaInteresDTO> actualizarTasaInteres(
            @PathVariable Integer id,
            @Valid @RequestBody TasaInteresDTO tasaInteresDTO) {
        log.info("Recibida solicitud para actualizar tasa de interés con ID: {}", id);
        
        TasaInteresDTO tasaActualizada = parametrosService.actualizarTasaInteres(id, tasaInteresDTO);
        
        return ResponseEntity.ok(tasaActualizada);
    }

    @GetMapping("/tasas-interes/{id}")
    public ResponseEntity<TasaInteresDTO> obtenerTasaPorId(@PathVariable Integer id) {
        log.info("Recibida solicitud para obtener tasa de interés con ID: {}", id);
        
        TasaInteresDTO tasa = parametrosService.obtenerTasaPorId(id);
        
        return ResponseEntity.ok(tasa);
    }

    @GetMapping("/productos-credito/{idProducto}/tasa-vigente")
    public ResponseEntity<TasaInteresDTO> obtenerTasaVigente(@PathVariable Integer idProducto) {
        log.info("Recibida solicitud para obtener tasa vigente del producto: {}", idProducto);
        
        TasaInteresDTO tasaVigente = parametrosService.obtenerTasaVigente(idProducto);
        
        return ResponseEntity.ok(tasaVigente);
    }

    @GetMapping("/productos-credito/{idProducto}/tasas-interes")
    public ResponseEntity<List<TasaInteresDTO>> obtenerTasasPorProducto(@PathVariable Integer idProducto) {
        log.info("Recibida solicitud para obtener tasas de interés del producto: {}", idProducto);
        
        List<TasaInteresDTO> tasas = parametrosService.obtenerTasasPorProducto(idProducto);
        
        return ResponseEntity.ok(tasas);
    }

    // ========== DOCUMENTOS REQUERIDOS ==========

    @PostMapping("/documentos-requeridos")
    public ResponseEntity<DocumentoRequeridoDTO> crearDocumentoRequerido(
            @Valid @RequestBody DocumentoRequeridoDTO documentoRequeridoDTO) {
        log.info("Recibida solicitud para crear documento requerido: {} para producto: {}", 
                documentoRequeridoDTO.getNombre(), documentoRequeridoDTO.getIdProductoCredito());
        
        DocumentoRequeridoDTO documentoCreado = parametrosService.crearDocumentoRequerido(documentoRequeridoDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(documentoCreado);
    }

    @PutMapping("/documentos-requeridos/{id}")
    public ResponseEntity<DocumentoRequeridoDTO> actualizarDocumentoRequerido(
            @PathVariable Integer id,
            @Valid @RequestBody DocumentoRequeridoDTO documentoRequeridoDTO) {
        log.info("Recibida solicitud para actualizar documento requerido con ID: {}", id);
        
        DocumentoRequeridoDTO documentoActualizado = parametrosService.actualizarDocumentoRequerido(id, documentoRequeridoDTO);
        
        return ResponseEntity.ok(documentoActualizado);
    }

    @GetMapping("/productos-credito/{idProducto}/documentos-requeridos")
    public ResponseEntity<List<DocumentoRequeridoDTO>> obtenerDocumentosPorProducto(@PathVariable Integer idProducto) {
        log.info("Recibida solicitud para obtener documentos requeridos del producto: {}", idProducto);
        
        List<DocumentoRequeridoDTO> documentos = parametrosService.obtenerDocumentosPorProducto(idProducto);
        
        return ResponseEntity.ok(documentos);
    }

    // ========== MANEJO DE EXCEPCIONES ==========

    @ExceptionHandler(ProductoCreditoNotFoundException.class)
    public ResponseEntity<String> manejarProductoNoEncontrado(ProductoCreditoNotFoundException ex) {
        log.error("Producto de crédito no encontrado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(TasaInteresNotFoundException.class)
    public ResponseEntity<String> manejarTasaNoEncontrada(TasaInteresNotFoundException ex) {
        log.error("Tasa de interés no encontrada: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DocumentoRequeridoNotFoundException.class)
    public ResponseEntity<String> manejarDocumentoNoEncontrado(DocumentoRequeridoNotFoundException ex) {
        log.error("Documento requerido no encontrado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ValidacionNegocioException.class)
    public ResponseEntity<String> manejarValidacionNegocio(ValidacionNegocioException ex) {
        log.error("Error de validación de negocio: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<String> manejarValidacionCampos(org.springframework.web.bind.MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getDefaultMessage())
            .findFirst()
            .orElse("Error de validación de campos");
        log.error("Error de validación de campos: {}", mensaje);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejarErrorGeneral(Exception ex) {
        log.error("Error interno del servidor: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor. Por favor, intente más tarde.");
    }
} 