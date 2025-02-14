package ec.com.banco.cuenta.infrastructure.cuenta.in.rest;


import ec.com.banco.cuenta.domain.common.exception.EntidadNoEncontradaException;
import ec.com.banco.cuenta.domain.common.exception.RegistroDuplicadoException;
import ec.com.banco.cuenta.domain.cuenta.services.CuentaService;
import ec.com.banco.cuenta.infrastructure.common.exceptions.ErrorResponse;
import ec.com.banco.cuenta.infrastructure.cuenta.mappers.CuentaMapper;
import ec.com.banco.cuenta.infrastructure.cuenta.mappers.FiltroMapper;
import ec.com.banco.cuenta.share.cuenta.dto.CuentaDto;
import ec.com.banco.cuenta.share.cuenta.dto.FiltroDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@Tag(name = "Cuenta", description = "Cuenta API")
public class CuentaController {

    private final CuentaService clienteService;
    private final CuentaMapper clienteMapper;
    private final FiltroMapper filtroMapper;

    public CuentaController(CuentaService clienteService, CuentaMapper clienteMapper, FiltroMapper filtroMapper) {
        this.clienteService = clienteService;
        this.clienteMapper = clienteMapper;
        this.filtroMapper = filtroMapper;
    }


    /**
     * Crear nueva Companias.
     *
     * @param crearDto Dto de crear compania
     * @return CompaniasCrearDto
     */
    @PostMapping
    @Operation(summary = "Crear nuevo Cuenta")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Cuenta creada."),
            @ApiResponse(responseCode = "400", description = "Entrada inválida.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Cuenta ya exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })

    public ResponseEntity<Void> crearCuenta(@Validated(CuentaDto.Crear.class) @RequestBody CuentaDto crearDto)
            throws RegistroDuplicadoException {
        this.clienteService.crearCuenta(this.clienteMapper.dtoToDomain(crearDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Actualizar Cuenta
     *
     * @param actualizarDto dto para actualizar cuenta
     */
    @PatchMapping()
    @Operation(summary = "Actualizar Cuenta")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cuenta ha sido actualizado."),
            @ApiResponse(responseCode = "404", description = "Cuenta no existe.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    public ResponseEntity<Void> actualizarCuenta(
            @Validated(CuentaDto.Actualizar.class) @RequestBody CuentaDto actualizarDto)
            throws EntidadNoEncontradaException {
        this.clienteService.actualizarCuenta(this.clienteMapper.dtoToDomain(actualizarDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Eliminar cuenta
     *
     * @param noCia Id company
     */
    @DeleteMapping("/{noCia}")
    @Operation(summary = "Eliminar Cuenta")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cuenta ha sido eliminada."),
            @ApiResponse(responseCode = "404", description = "Cuenta no existe.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    public ResponseEntity<Void> eliminarCuenta(@NotNull @PathVariable Long noCia)
            throws EntidadNoEncontradaException {
        clienteService.eliminarCuenta(noCia);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Buscar Cuenta por codigo.
     *
     * @return CuentaDto
     * @author ksuarez on 2024/04/17.
     */
    @PostMapping("/obtenerPorFiltros")
    @Operation(summary = "Obtener Cuenta por id")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cuenta encontrada."),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    public ResponseEntity<List<CuentaDto>> obtenerCuentas(@RequestBody FiltroDto filtro)
            {
                try {
                    return new ResponseEntity<>(
                            this.clienteMapper.domainsToDtos(clienteService.obtenerCuentas(this.filtroMapper.dtoToDomain(filtro))),
                            HttpStatus.OK);
                } catch (EntidadNoEncontradaException e) {
                    throw new RuntimeException(e);
                }
            }

}
