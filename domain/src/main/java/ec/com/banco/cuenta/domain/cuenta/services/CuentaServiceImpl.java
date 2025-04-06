package ec.com.banco.cuenta.domain.cuenta.services;

import ec.com.banco.cuenta.domain.common.constants.ClienteExceptionMessages;
import ec.com.banco.cuenta.domain.common.exception.EntidadNoEncontradaException;
import ec.com.banco.cuenta.domain.cuenta.models.Cuenta;
import ec.com.banco.cuenta.domain.cuenta.models.Filtro;
import ec.com.banco.cuenta.domain.cuenta.repositories.CuentaRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository clienteRepository;
    private final MessageSource messageSource;

    public CuentaServiceImpl(CuentaRepository clienteRepository, MessageSource messageSource) {
        this.clienteRepository = clienteRepository;
        this.messageSource = messageSource;
    }

    @Override
    public void crearCuenta(Cuenta cuenta) {

        clienteRepository.crearCuenta(cuenta);
    }

    @Override
    public void actualizarCuenta(Cuenta cuenta) throws EntidadNoEncontradaException {
        clienteRepository.actualizarCuenta2(cuenta);
    }

    @Override
    public void eliminarCuenta(Long cuentaId) throws EntidadNoEncontradaException {
        if (this.clienteRepository.obtenerCuenta(cuentaId) == null) {
            throw new EntidadNoEncontradaException(messageSource.getMessage(ClienteExceptionMessages.ERROR_NO_EXISTE,
                    null, LocaleContextHolder.getLocale()));
        }
        clienteRepository.eliminarCuenta(cuentaId);
    }

    @Override
    public Cuenta obtenerCuenta(Long cuentaId) throws EntidadNoEncontradaException {
        return clienteRepository.obtenerCuenta(cuentaId);
    }

    @Override
    public List<Cuenta> obtenerCuentas(Filtro filtro) throws EntidadNoEncontradaException {
        return clienteRepository.obtenerCuentas(filtro);
    }

    @Override
    public List<Cuenta> obtenerCuentaPorFiltros(Filtro filtro) throws EntidadNoEncontradaException {
        return clienteRepository.obtenerCuentaPorFiltros(filtro);
    }

    @Override
    public List<Cuenta> obtenerListadoCuentas() {
        return clienteRepository.obtenerListadoCuentas();
    }

}
