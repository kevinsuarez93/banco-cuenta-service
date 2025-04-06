package ec.com.banco.cuenta.domain.cuenta.services;

import ec.com.banco.cuenta.domain.common.exception.EntidadNoEncontradaException;
import ec.com.banco.cuenta.domain.cuenta.models.Cuenta;
import ec.com.banco.cuenta.domain.cuenta.models.Filtro;

import java.util.List;

public interface CuentaService {
    void crearCuenta(Cuenta cuenta);

    void actualizarCuenta(Cuenta cuenta) throws EntidadNoEncontradaException;

    void eliminarCuenta(Long cuentaId) throws EntidadNoEncontradaException;

    Cuenta obtenerCuenta(Long cuentaId) throws EntidadNoEncontradaException;

    List<Cuenta> obtenerCuentas(Filtro filtro) throws EntidadNoEncontradaException;

    List<Cuenta> obtenerCuentaPorFiltros(Filtro filtro) throws EntidadNoEncontradaException;

    List<Cuenta> obtenerListadoCuentas();

}
