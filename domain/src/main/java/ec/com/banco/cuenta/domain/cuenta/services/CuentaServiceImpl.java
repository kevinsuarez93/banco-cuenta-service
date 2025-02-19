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
    public void crearCuenta(Cuenta cliente) {

        clienteRepository.crearCuenta(cliente);
    }

    @Override
    public void actualizarCuenta(Cuenta cliente) throws EntidadNoEncontradaException {
        clienteRepository.actualizarCuenta2(cliente);
    }

    @Override
    public void eliminarCuenta(Long clienteId) throws EntidadNoEncontradaException {
        if (this.clienteRepository.obtenerCuenta(clienteId) == null) {
            throw new EntidadNoEncontradaException(messageSource.getMessage(ClienteExceptionMessages.ERROR_NO_EXISTE,
                    null, LocaleContextHolder.getLocale()));
        }
        clienteRepository.eliminarCuenta(clienteId);
    }

    @Override
    public Cuenta obtenerCuenta(Long clienteId) throws EntidadNoEncontradaException {
        return clienteRepository.obtenerCuenta(clienteId);
    }

    @Override
    public List<Cuenta> obtenerCuentas(Filtro filtro) throws EntidadNoEncontradaException {
        return clienteRepository.obtenerCuentas(filtro);
    }

    @Override
    public List<Cuenta> obtenerCuentaPorFiltros(Filtro filtro) throws EntidadNoEncontradaException {
        return clienteRepository.obtenerCuentaPorFiltros(filtro);
    }
}
