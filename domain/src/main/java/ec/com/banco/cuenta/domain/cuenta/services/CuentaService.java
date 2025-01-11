package ec.com.banco.cuenta.domain.cuenta.services;

import ec.com.banco.cuenta.domain.common.exception.EntidadNoEncontradaException;
import ec.com.banco.cuenta.domain.cuenta.models.Cuenta;

public interface CuentaService {
    void crearCuenta(Cuenta cliente);

    void actualizarCuenta(Cuenta cliente) throws EntidadNoEncontradaException;

    void eliminarCuenta(Long clienteId) throws EntidadNoEncontradaException;
}
