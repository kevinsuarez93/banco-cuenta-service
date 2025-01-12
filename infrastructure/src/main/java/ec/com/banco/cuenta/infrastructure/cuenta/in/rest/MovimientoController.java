package ec.com.banco.cuenta.infrastructure.cuenta.in.rest;

import ec.com.banco.cuenta.domain.common.exception.EntidadNoEncontradaException;
import ec.com.banco.cuenta.domain.common.exception.RegistroDuplicadoException;
import ec.com.banco.cuenta.domain.common.exception.ReglaDeNegocioException;
import ec.com.banco.cuenta.domain.cuenta.services.MovimientoService;
import ec.com.banco.cuenta.infrastructure.common.exceptions.ErrorResponse;
import ec.com.banco.cuenta.infrastructure.cuenta.mappers.MovimientoMapper;
import ec.com.banco.cuenta.share.cuenta.dto.MovimientoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movimientos")
@Tag(name = "Movimiento", description = "Movimiento API")
public class MovimientoController {

    private final MovimientoService movimientoService;
    private final MovimientoMapper movimientoMapper;

    public MovimientoController(MovimientoService movimientoService, MovimientoMapper movimientoMapper) {
        this.movimientoService = movimientoService;
        this.movimientoMapper = movimientoMapper;
    }


    /**
     * Crear nueva Companias.
     *
     * @param crearDto Dto de crear compania
     * @return CompaniasCrearDto
     */
    @PostMapping
    @Operation(summary = "Crear nuevo Movimiento")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Movimiento creada."),
            @ApiResponse(responseCode = "400", description = "Entrada inválida.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Movimiento ya exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })

    public ResponseEntity<Void> crearCuenta(@Validated(MovimientoDto.Crear.class) @RequestBody MovimientoDto crearDto)
            throws RegistroDuplicadoException {
        this.movimientoService.crearMovimiento(this.movimientoMapper.dtoToDomain(crearDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Actualizar Compania
     *
     * @param actualizarDto dto para actualizar compania
     * @return CompaniasActualizarDto
     * @throws EntidadNoEncontradaException
     */
    @PatchMapping()
    @Operation(summary = "Actualizar Movimiento")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Movimiento ha sido actualizado."),
            @ApiResponse(responseCode = "404", description = "Movimiento no existe.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    public ResponseEntity<Void> actualizarCuenta(
            @Validated(MovimientoDto.Actualizar.class) @RequestBody MovimientoDto actualizarDto)
            throws EntidadNoEncontradaException {
        this.movimientoService.actualizarMovimiento(this.movimientoMapper.dtoToDomain(actualizarDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Eliminar compania
     *
     * @param noCia Id company
     */
    @DeleteMapping("/{movimientoId}")
    @Operation(summary = "Eliminar Movimiento")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Movimiento ha sido eliminada."),
            @ApiResponse(responseCode = "404", description = "Movimiento no existe.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    public ResponseEntity<Void> eliminarCuenta(@NotBlank @PathVariable Long movimientoId)
            throws EntidadNoEncontradaException {
        movimientoService.eliminarMovimiento(movimientoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Crear nueva Companias.
     *
     * @param crearDto Dto de crear compania
     * @return CompaniasCrearDto
     */
    @PostMapping("/registrar-movimiento")
    @Operation(summary = "Crear nuevo Movimiento")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Movimiento creada."),
            @ApiResponse(responseCode = "400", description = "Entrada inválida.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Movimiento ya exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })

    public ResponseEntity<Void> registrarMovimiento(@RequestBody MovimientoDto movimientoDto)
            throws RegistroDuplicadoException, EntidadNoEncontradaException, ReglaDeNegocioException {
        this.movimientoService.registrarMovimiento(this.movimientoMapper.dtoToDomain(movimientoDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
