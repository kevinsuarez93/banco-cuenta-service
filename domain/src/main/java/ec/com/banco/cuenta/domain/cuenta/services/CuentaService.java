package ec.com.banco.cuenta.domain.cuenta.services;

import ec.com.banco.cuenta.domain.common.exception.EntidadNoEncontradaException;
import ec.com.banco.cuenta.domain.cuenta.models.Cuenta;
import ec.com.banco.cuenta.domain.cuenta.models.Filtro;

import java.util.List;

public interface CuentaService {
    void crearCuenta(Cuenta cliente);

    void actualizarCuenta(Cuenta cliente) throws EntidadNoEncontradaException;

    void eliminarCuenta(Long clienteId) throws EntidadNoEncontradaException;

    Cuenta obtenerCuenta(Long clienteId) throws EntidadNoEncontradaException;

    List<Cuenta> obtenerCuentas(Filtro filtro) throws EntidadNoEncontradaException;

    List<Cuenta> obtenerCuentaPorFiltros(Filtro filtro) throws EntidadNoEncontradaException;

}
