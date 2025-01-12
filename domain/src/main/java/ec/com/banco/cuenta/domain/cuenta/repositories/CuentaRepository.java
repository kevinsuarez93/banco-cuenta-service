package ec.com.banco.cuenta.domain.cuenta.repositories;

import ec.com.banco.cuenta.domain.common.exception.EntidadNoEncontradaException;
import ec.com.banco.cuenta.domain.cuenta.models.Cuenta;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface CuentaRepository {

    void crearCuenta(Cuenta cliente);

    void actualizarCuenta(Cuenta cliente) throws EntidadNoEncontradaException;

    Cuenta obtenerCuenta(Long clienteId);

    void eliminarCuenta(Long clienteId) throws EntidadNoEncontradaException;

    List<Cuenta> obtenerCuentas(Date fechaInicio, Date fechaFin, Long clienteId);
}
