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
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    private MovimientoRepository movimientoRepository;
    private MessageSource messageSource;
    private CuentaService cuentaService;

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

        Cuenta cuenta = this.cuentaService.obtenerCuenta(movimiento.getCuentaId());

        if (cuenta == null) {
            throw new EntidadNoEncontradaException(
                    messageSource.getMessage(ClienteExceptionMessages.ERROR_NO_EXISTE, null, LocaleContextHolder.getLocale()));
        }

        BigDecimal saldoActual = cuenta.getSaldoInicial();
        BigDecimal nuevoSaldo = saldoActual.add(movimiento.getValor());

        // Validar saldo disponible en caso de retiro
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new ReglaDeNegocioException(messageSource.getMessage(ClienteExceptionMessages.NEGOCIO_SALDO_NO_DISPONIBLE,
                    null, LocaleContextHolder.getLocale()));
        }

        // Asignar fecha y saldo actualizado al movimiento
        movimiento.setFecha(new Date());
        movimiento.setSaldo(nuevoSaldo);

        // Guardar el movimiento en la base de datos
        this.crearMovimiento(movimiento);

        // Actualizar saldo en la cuenta
        cuenta.setSaldoInicial(nuevoSaldo);
        this.cuentaService.actualizarCuenta(cuenta);

    }
}
