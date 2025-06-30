package com.banquito.parametros.controller;

import com.banquito.parametros.controller.dto.DocumentoRequeridoDTO;
import com.banquito.parametros.controller.dto.ProductoCreditoDTO;
import com.banquito.parametros.controller.dto.TasaInteresDTO;
import com.banquito.parametros.exception.*;
import com.banquito.parametros.service.ParametrosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parametros")
@Tag(name = "Parámetros de Crédito Automotriz", description = "API para gestión de parámetros de préstamos automotrices")
public class ParametrosController {

    private static final Logger log = LoggerFactory.getLogger(ParametrosController.class);
    
    private final ParametrosService parametrosService;

    public ParametrosController(ParametrosService parametrosService) {
        this.parametrosService = parametrosService;
    }

    // ========== PRODUCTOS DE CRÉDITO ==========

    @PostMapping("/productos-credito")
    @Operation(summary = "Crear producto de crédito", description = "Crea un nuevo producto de crédito automotriz")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
                content = @Content(schema = @Schema(implementation = ProductoCreditoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o reglas de negocio violadas"),
        @ApiResponse(responseCode = "409", description = "Conflicto - código de producto ya existe")
    })
    public ResponseEntity<ProductoCreditoDTO> crearProductoCredito(
            @Valid @RequestBody ProductoCreditoDTO productoCreditoDTO) {
        log.info("Recibida solicitud para crear producto de crédito: {}", productoCreditoDTO.getCodigoProducto());
        
        ProductoCreditoDTO productoCreado = parametrosService.crearProductoCredito(productoCreditoDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
    }

    @PutMapping("/productos-credito/{id}")
    @Operation(summary = "Actualizar producto de crédito", description = "Actualiza un producto de crédito existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
                content = @Content(schema = @Schema(implementation = ProductoCreditoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o reglas de negocio violadas"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductoCreditoDTO> actualizarProductoCredito(
            @Parameter(description = "ID del producto de crédito") @PathVariable Integer id,
            @Valid @RequestBody ProductoCreditoDTO productoCreditoDTO) {
        log.info("Recibida solicitud para actualizar producto de crédito con ID: {}", id);
        
        ProductoCreditoDTO productoActualizado = parametrosService.actualizarProductoCredito(id, productoCreditoDTO);
        
        return ResponseEntity.ok(productoActualizado);
    }

    @GetMapping("/productos-credito/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Obtiene un producto de crédito específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto obtenido exitosamente",
                content = @Content(schema = @Schema(implementation = ProductoCreditoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductoCreditoDTO> obtenerProductoPorId(
            @Parameter(description = "ID del producto de crédito") @PathVariable Integer id) {
        log.info("Recibida solicitud para obtener producto de crédito con ID: {}", id);
        
        ProductoCreditoDTO producto = parametrosService.obtenerProductoPorId(id);
        
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/productos-credito/codigo/{codigo}")
    @Operation(summary = "Obtener producto por código", description = "Obtiene un producto de crédito específico por su código")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto obtenido exitosamente",
                content = @Content(schema = @Schema(implementation = ProductoCreditoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductoCreditoDTO> obtenerProductoPorCodigo(
            @Parameter(description = "Código del producto de crédito") @PathVariable String codigo) {
        log.info("Recibida solicitud para obtener producto de crédito con código: {}", codigo);
        
        ProductoCreditoDTO producto = parametrosService.obtenerProductoPorCodigo(codigo);
        
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/productos-credito/activos")
    @Operation(summary = "Obtener productos activos", description = "Obtiene la lista de productos de crédito activos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
                content = @Content(schema = @Schema(implementation = ProductoCreditoDTO.class)))
    })
    public ResponseEntity<List<ProductoCreditoDTO>> obtenerProductosActivos() {
        log.info("Recibida solicitud para obtener productos de crédito activos");
        
        List<ProductoCreditoDTO> productos = parametrosService.obtenerProductosActivos();
        
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/productos-credito")
    @Operation(summary = "Obtener todos los productos", description = "Obtiene la lista completa de productos de crédito")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
                content = @Content(schema = @Schema(implementation = ProductoCreditoDTO.class)))
    })
    public ResponseEntity<List<ProductoCreditoDTO>> obtenerTodosLosProductos() {
        log.info("Recibida solicitud para obtener todos los productos de crédito");
        
        List<ProductoCreditoDTO> productos = parametrosService.obtenerTodosLosProductos();
        
        return ResponseEntity.ok(productos);
    }

    // ========== TASAS DE INTERÉS ==========

    @PostMapping("/tasas-interes")
    @Operation(summary = "Crear tasa de interés", description = "Crea una nueva tasa de interés para un producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tasa creada exitosamente",
                content = @Content(schema = @Schema(implementation = TasaInteresDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o reglas de negocio violadas"),
        @ApiResponse(responseCode = "409", description = "Conflicto - traslape con tasa existente")
    })
    public ResponseEntity<TasaInteresDTO> crearTasaInteres(
            @Valid @RequestBody TasaInteresDTO tasaInteresDTO) {
        log.info("Recibida solicitud para crear tasa de interés para producto: {}", tasaInteresDTO.getIdProductoCredito());
        
        TasaInteresDTO tasaCreada = parametrosService.crearTasaInteres(tasaInteresDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(tasaCreada);
    }

    @PutMapping("/tasas-interes/{id}")
    @Operation(summary = "Actualizar tasa de interés", description = "Actualiza una tasa de interés existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tasa actualizada exitosamente",
                content = @Content(schema = @Schema(implementation = TasaInteresDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o reglas de negocio violadas"),
        @ApiResponse(responseCode = "404", description = "Tasa no encontrada")
    })
    public ResponseEntity<TasaInteresDTO> actualizarTasaInteres(
            @Parameter(description = "ID de la tasa de interés") @PathVariable Integer id,
            @Valid @RequestBody TasaInteresDTO tasaInteresDTO) {
        log.info("Recibida solicitud para actualizar tasa de interés con ID: {}", id);
        
        TasaInteresDTO tasaActualizada = parametrosService.actualizarTasaInteres(id, tasaInteresDTO);
        
        return ResponseEntity.ok(tasaActualizada);
    }

    @GetMapping("/tasas-interes/{id}")
    @Operation(summary = "Obtener tasa por ID", description = "Obtiene una tasa de interés específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tasa obtenida exitosamente",
                content = @Content(schema = @Schema(implementation = TasaInteresDTO.class))),
        @ApiResponse(responseCode = "404", description = "Tasa no encontrada")
    })
    public ResponseEntity<TasaInteresDTO> obtenerTasaPorId(
            @Parameter(description = "ID de la tasa de interés") @PathVariable Integer id) {
        log.info("Recibida solicitud para obtener tasa de interés con ID: {}", id);
        
        TasaInteresDTO tasa = parametrosService.obtenerTasaPorId(id);
        
        return ResponseEntity.ok(tasa);
    }

    @GetMapping("/productos-credito/{idProducto}/tasa-vigente")
    @Operation(summary = "Obtener tasa vigente", description = "Obtiene la tasa de interés vigente para un producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tasa vigente obtenida exitosamente",
                content = @Content(schema = @Schema(implementation = TasaInteresDTO.class))),
        @ApiResponse(responseCode = "404", description = "Tasa vigente no encontrada")
    })
    public ResponseEntity<TasaInteresDTO> obtenerTasaVigente(
            @Parameter(description = "ID del producto de crédito") @PathVariable Integer idProducto) {
        log.info("Recibida solicitud para obtener tasa vigente del producto: {}", idProducto);
        
        TasaInteresDTO tasaVigente = parametrosService.obtenerTasaVigente(idProducto);
        
        return ResponseEntity.ok(tasaVigente);
    }

    @GetMapping("/productos-credito/{idProducto}/tasas-interes")
    @Operation(summary = "Obtener tasas por producto", description = "Obtiene todas las tasas de interés de un producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tasas obtenida exitosamente",
                content = @Content(schema = @Schema(implementation = TasaInteresDTO.class)))
    })
    public ResponseEntity<List<TasaInteresDTO>> obtenerTasasPorProducto(
            @Parameter(description = "ID del producto de crédito") @PathVariable Integer idProducto) {
        log.info("Recibida solicitud para obtener tasas de interés del producto: {}", idProducto);
        
        List<TasaInteresDTO> tasas = parametrosService.obtenerTasasPorProducto(idProducto);
        
        return ResponseEntity.ok(tasas);
    }

    // ========== DOCUMENTOS REQUERIDOS ==========

    @PostMapping("/documentos-requeridos")
    @Operation(summary = "Crear documento requerido", description = "Crea un nuevo documento requerido para un producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Documento creado exitosamente",
                content = @Content(schema = @Schema(implementation = DocumentoRequeridoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o reglas de negocio violadas"),
        @ApiResponse(responseCode = "409", description = "Conflicto - documento con mismo nombre ya existe")
    })
    public ResponseEntity<DocumentoRequeridoDTO> crearDocumentoRequerido(
            @Valid @RequestBody DocumentoRequeridoDTO documentoRequeridoDTO) {
        log.info("Recibida solicitud para crear documento requerido: {} para producto: {}", 
                documentoRequeridoDTO.getNombre(), documentoRequeridoDTO.getIdProductoCredito());
        
        DocumentoRequeridoDTO documentoCreado = parametrosService.crearDocumentoRequerido(documentoRequeridoDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(documentoCreado);
    }

    @PutMapping("/documentos-requeridos/{id}")
    @Operation(summary = "Actualizar documento requerido", description = "Actualiza un documento requerido existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Documento actualizado exitosamente",
                content = @Content(schema = @Schema(implementation = DocumentoRequeridoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o reglas de negocio violadas"),
        @ApiResponse(responseCode = "404", description = "Documento no encontrado")
    })
    public ResponseEntity<DocumentoRequeridoDTO> actualizarDocumentoRequerido(
            @Parameter(description = "ID del documento requerido") @PathVariable Integer id,
            @Valid @RequestBody DocumentoRequeridoDTO documentoRequeridoDTO) {
        log.info("Recibida solicitud para actualizar documento requerido con ID: {}", id);
        
        DocumentoRequeridoDTO documentoActualizado = parametrosService.actualizarDocumentoRequerido(id, documentoRequeridoDTO);
        
        return ResponseEntity.ok(documentoActualizado);
    }

    @GetMapping("/documentos-requeridos/{id}")
    @Operation(summary = "Obtener documento por ID", description = "Obtiene un documento requerido específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Documento obtenido exitosamente",
                content = @Content(schema = @Schema(implementation = DocumentoRequeridoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Documento no encontrado")
    })
    public ResponseEntity<DocumentoRequeridoDTO> obtenerDocumentoPorId(
            @Parameter(description = "ID del documento requerido") @PathVariable Integer id) {
        log.info("Recibida solicitud para obtener documento requerido con ID: {}", id);
        
        DocumentoRequeridoDTO documento = parametrosService.obtenerDocumentoPorId(id);
        
        return ResponseEntity.ok(documento);
    }

    @GetMapping("/productos-credito/{idProducto}/documentos-requeridos")
    @Operation(summary = "Obtener documentos requeridos", description = "Obtiene la lista de documentos requeridos para un producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de documentos obtenida exitosamente",
                content = @Content(schema = @Schema(implementation = DocumentoRequeridoDTO.class)))
    })
    public ResponseEntity<List<DocumentoRequeridoDTO>> obtenerDocumentosPorProducto(
            @Parameter(description = "ID del producto de crédito") @PathVariable Integer idProducto) {
        log.info("Recibida solicitud para obtener documentos requeridos del producto: {}", idProducto);
        
        List<DocumentoRequeridoDTO> documentos = parametrosService.obtenerDocumentosPorProducto(idProducto);
        
        return ResponseEntity.ok(documentos);
    }

    @GetMapping("/documentos-requeridos")
    @Operation(summary = "Obtener todos los documentos", description = "Obtiene la lista completa de documentos requeridos activos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de documentos obtenida exitosamente",
                content = @Content(schema = @Schema(implementation = DocumentoRequeridoDTO.class)))
    })
    public ResponseEntity<List<DocumentoRequeridoDTO>> obtenerTodosLosDocumentos() {
        log.info("Recibida solicitud para obtener todos los documentos requeridos");
        
        List<DocumentoRequeridoDTO> documentos = parametrosService.obtenerTodosLosDocumentos();
        
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