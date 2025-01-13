package ec.com.banco.cuenta.infrastructure.ejemplo.in.jms.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.com.banco.cuenta.domain.common.exception.EntidadNoEncontradaException;
import ec.com.banco.cuenta.domain.cuenta.models.Cuenta;
import ec.com.banco.cuenta.domain.cuenta.services.CuentaService;
import ec.com.banco.cuenta.infrastructure.common.jms.Servicio;
import ec.com.banco.cuenta.infrastructure.cuenta.mappers.CuentaMapper;
import ec.com.banco.cuenta.infrastructure.cuenta.mappers.FiltroMapper;
import ec.com.banco.cuenta.share.cuenta.dto.FiltroDto;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BuscarCuentaOperation implements Servicio {

    private CuentaService cuentaService;
    private CuentaMapper cuentaMapper;
    private FiltroMapper filtroMapper;

    public BuscarCuentaOperation(CuentaService cuentaService, CuentaMapper cuentaMapper, FiltroMapper filtroMapper) {
        this.cuentaService = cuentaService;
        this.cuentaMapper = cuentaMapper;
        this.filtroMapper = filtroMapper;
    }

    @Override
    public String execute(TextMessage textMessage) throws JsonProcessingException, JMSException, EntidadNoEncontradaException {
        log.info("textMessage: "+ textMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        FiltroDto filtroDto = objectMapper.readValue(textMessage.getText(), FiltroDto.class);

        List<Cuenta> cuentas = this.cuentaService.obtenerCuentaPorFiltros(this.filtroMapper.dtoToDomain(filtroDto));
        if (cuentas == null) {
            throw new EntidadNoEncontradaException("No existe compania con el codigo " + textMessage.getText());
        }
        return objectMapper.writeValueAsString(cuentaMapper.domainsToDtos(cuentas));
    }
}
