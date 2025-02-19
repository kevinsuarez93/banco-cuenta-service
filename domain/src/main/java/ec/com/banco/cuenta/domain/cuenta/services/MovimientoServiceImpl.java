package ec.com.banco.cuenta.domain.cuenta.services;

import ec.com.banco.cuenta.domain.common.constants.ClienteExceptionMessages;
import ec.com.banco.cuenta.domain.common.exception.EntidadNoEncontradaException;
import ec.com.banco.cuenta.domain.common.exception.ReglaDeNegocioException;
import ec.com.banco.cuenta.domain.cuenta.models.Cuenta;
import ec.com.banco.cuenta.domain.cuenta.models.Movimiento;
import ec.com.banco.cuenta.domain.cuenta.repositories.MovimientoRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final MessageSource messageSource;
    private final CuentaService cuentaService;

    public MovimientoServiceImpl(MovimientoRepository movimientoRepository, MessageSource messageSource, CuentaService cuentaService) {
        this.movimientoRepository = movimientoRepository;
        this.messageSource = messageSource;
        this.cuentaService = cuentaService;
    }

    @Override
    public void crearMovimiento(Movimiento movimiento) {

        movimientoRepository.crearMovimiento(movimiento);
    }

    @Override
    public void actualizarMovimiento(Movimiento movimiento) throws EntidadNoEncontradaException {
        movimientoRepository.actualizarMovimiento(movimiento);
    }

    @Override
    public void eliminarMovimiento(Long movimientoId) throws EntidadNoEncontradaException {
        if (this.movimientoRepository.obtenerMovimiento(movimientoId) == null) {
            throw new EntidadNoEncontradaException(messageSource.getMessage(ClienteExceptionMessages.ERROR_NO_EXISTE,
                    null, LocaleContextHolder.getLocale()));
        }
        movimientoRepository.eliminarMovimiento(movimientoId);
    }

    @Override
    public void registrarMovimiento(Movimiento movimiento) throws EntidadNoEncontradaException, ReglaDeNegocioException {

        // 1. Validar existencia de la cuenta
        Cuenta cuenta = validarCuentaExiste(movimiento.getCuentaId());

        // 2. Calcular y validar saldo
        BigDecimal saldoActual = cuenta.getSaldoInicial();
        BigDecimal nuevoSaldo = saldoActual.add(movimiento.getValor());
        validarSaldoPositivo(nuevoSaldo);

        // 3. Completar datos del movimiento
        asignarDatosMovimiento(movimiento, nuevoSaldo);

        // 4. Guardar el movimiento
        this.crearMovimiento(movimiento);

        // 5. Actualizar saldo de la cuenta
        actualizarSaldoCuenta(cuenta, nuevoSaldo);
    }

    private Cuenta validarCuentaExiste(Long cuentaId) throws EntidadNoEncontradaException {
        Cuenta cuenta = cuentaService.obtenerCuenta(cuentaId);
        if (cuenta == null) {
            throw new EntidadNoEncontradaException(
                    messageSource.getMessage(ClienteExceptionMessages.ERROR_NO_EXISTE, null, LocaleContextHolder.getLocale())
            );
        }
        return cuenta;
    }

    private void validarSaldoPositivo(BigDecimal nuevoSaldo) throws ReglaDeNegocioException {
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new ReglaDeNegocioException(
                    messageSource.getMessage(ClienteExceptionMessages.NEGOCIO_SALDO_NO_DISPONIBLE, null, LocaleContextHolder.getLocale())
            );
        }
    }

    private void asignarDatosMovimiento(Movimiento movimiento, BigDecimal nuevoSaldo) {
        // Asignar fecha y saldo
        movimiento.setFecha(new Date());
        movimiento.setSaldo(nuevoSaldo);
    }

    private void actualizarSaldoCuenta(Cuenta cuenta, BigDecimal nuevoSaldo) throws EntidadNoEncontradaException {
        cuenta.setSaldoInicial(nuevoSaldo);
        this.cuentaService.actualizarCuenta(cuenta);
    }
}
