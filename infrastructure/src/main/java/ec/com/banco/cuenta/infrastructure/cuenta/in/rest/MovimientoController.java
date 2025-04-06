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
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * Crear nueva Movimiento.
     *
     * @param crearDto Dto de crear Movimiento
     * @return Void
     */
    @PostMapping
    @Operation(summary = "Crear nuevo Movimiento")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Movimiento creada."),
            @ApiResponse(responseCode = "400", description = "Entrada inválida.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Movimiento ya exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })

    public ResponseEntity<Void> crearMovimiento(@Validated(MovimientoDto.Crear.class) @RequestBody MovimientoDto crearDto)
            throws RegistroDuplicadoException {
        this.movimientoService.crearMovimiento(this.movimientoMapper.dtoToDomain(crearDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Actualizar Movimiento
     *
     * @param actualizarDto dto para actualizar movimiento
     * @throws EntidadNoEncontradaException
     */
    @PatchMapping()
    @Operation(summary = "Actualizar Movimiento")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Movimiento ha sido actualizado."),
            @ApiResponse(responseCode = "404", description = "Movimiento no existe.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    public ResponseEntity<Void> actualizarMovimiento(
            @Validated(MovimientoDto.Actualizar.class) @RequestBody MovimientoDto actualizarDto)
            throws EntidadNoEncontradaException {
        this.movimientoService.actualizarMovimiento(this.movimientoMapper.dtoToDomain(actualizarDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Buscar todas las cuentas.
     *
     * @return Lista de CompaniasDto
     * @author ksuarez on 2024/04/17.
     */
    @GetMapping()
    @Operation(summary = "Obtener todas las cuentas")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "cuentas encontradas."),
            @ApiResponse(responseCode = "404", description = "cuentas no existe.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    public ResponseEntity<List<MovimientoDto>> obtenerListadoMovimientos() {
        return new ResponseEntity<>(
                this.movimientoMapper.domainsToDtos(movimientoService.obtenerListadoMovimientos()), HttpStatus.OK);
    }

    /**
     * Buscar cuentas por codigo.
     *
     * @param movimientoId codigo de compania
     * @return CompaniasDto
     * @author ksuarez on 2024/04/17.
     */
    @GetMapping(value = "/{movimientoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtener cuentas por id")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "cuentas encontrada."),
            @ApiResponse(responseCode = "404", description = "cuentas no encontrada.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    public ResponseEntity<MovimientoDto> obtenerMovimiento(@NotNull @PathVariable Long movimientoId)
            {
        return new ResponseEntity<>(this.movimientoMapper.domainToDto(movimientoService.obtenerMovimiento(movimientoId)),
                HttpStatus.OK);
    }

    /**
     * Eliminar movimiento
     *
     * @param movimientoId Id de movimiento
     */
    @DeleteMapping("/{movimientoId}")
    @Operation(summary = "Eliminar Movimiento")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Movimiento ha sido eliminada."),
            @ApiResponse(responseCode = "404", description = "Movimiento no existe.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    public ResponseEntity<Void> eliminarMovimiento(@NotNull @PathVariable Long movimientoId)
            throws EntidadNoEncontradaException {
        movimientoService.eliminarMovimiento(movimientoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Registrar movimiento.
     *
     * @param movimientoDto Dto de crear movimiento
     */
    @PostMapping("/registrar-movimiento")
    @Operation(summary = "Registrar nuevo Movimiento")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Movimiento creada."),
            @ApiResponse(responseCode = "400", description = "Entrada inválida.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Movimiento ya exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })

    public ResponseEntity<Void> registrarMovimiento(@RequestBody MovimientoDto movimientoDto)
            throws RegistroDuplicadoException, EntidadNoEncontradaException, ReglaDeNegocioException {
        this.movimientoService.registrarMovimiento(this.movimientoMapper.dtoToDomain(movimientoDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
