package ec.com.banco.cuenta.domain.cuenta.repositories;

import ec.com.banco.cuenta.domain.common.exception.EntidadNoEncontradaException;
import ec.com.banco.cuenta.domain.cuenta.models.Movimiento;

import java.util.List;

public interface MovimientoRepository {

    void crearMovimiento(Movimiento cliente);

    void actualizarMovimiento(Movimiento cliente) throws EntidadNoEncontradaException;

    Movimiento obtenerMovimiento(Long movimientoId);

    void eliminarMovimiento(Long movimientoId) throws EntidadNoEncontradaException;

    List<Movimiento> obtenerListadoMovimientos();
}
