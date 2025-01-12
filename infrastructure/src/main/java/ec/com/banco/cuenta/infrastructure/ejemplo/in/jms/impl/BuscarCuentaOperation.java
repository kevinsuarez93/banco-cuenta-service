package ec.com.banco.cuenta.infrastructure.ejemplo.in.jms.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.com.banco.cuenta.domain.common.exception.EntidadNoEncontradaException;
import ec.com.banco.cuenta.domain.cuenta.models.Cuenta;
import ec.com.banco.cuenta.domain.cuenta.services.CuentaService;
import ec.com.banco.cuenta.infrastructure.common.jms.Servicio;
import ec.com.banco.cuenta.infrastructure.cuenta.mappers.CuentaMapper;
import ec.com.banco.cuenta.share.cuenta.dto.CuentaDto;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BuscarCuentaOperation implements Servicio {

    private CuentaService cuentaService;
    private CuentaMapper cuentaMapper;

    public BuscarCuentaOperation(CuentaService cuentaService, CuentaMapper cuentaMapper) {
        this.cuentaService = cuentaService;
        this.cuentaMapper = cuentaMapper;
    }

    @Override
    public String execute(TextMessage textMessage) throws JsonProcessingException, JMSException, EntidadNoEncontradaException {
        log.info("textMessage: "+ textMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        CuentaDto cuentaDto = objectMapper.readValue(textMessage.getText(), CuentaDto.class);

        Cuenta cuenta = this.cuentaService.obtenerCuentaPorFiltros(1L);
        if (cuenta == null) {
            throw new EntidadNoEncontradaException("No existe compania con el codigo " + textMessage.getText());
        }
        return objectMapper.writeValueAsString(cuentaMapper.domainToDto(cuenta));
    }
}
