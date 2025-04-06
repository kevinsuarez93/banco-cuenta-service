package ec.com.banco.cuenta.domain.cuenta.services;

import ec.com.banco.cuenta.domain.common.exception.EntidadNoEncontradaException;
import ec.com.banco.cuenta.domain.common.exception.ReglaDeNegocioException;
import ec.com.banco.cuenta.domain.cuenta.models.Movimiento;

import java.util.List;

public interface MovimientoService {
    void crearMovimiento(Movimiento movimiento);

    void actualizarMovimiento(Movimiento movimiento) throws EntidadNoEncontradaException;

    void eliminarMovimiento(Long movimientoId) throws EntidadNoEncontradaException;

    void registrarMovimiento(Movimiento movimiento) throws EntidadNoEncontradaException, ReglaDeNegocioException;

    List<Movimiento> obtenerListadoMovimientos();

    Movimiento obtenerMovimiento(Long movimientoId);
}
