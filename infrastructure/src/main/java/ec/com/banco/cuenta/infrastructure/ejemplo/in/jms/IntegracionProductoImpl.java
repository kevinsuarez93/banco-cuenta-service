package ec.com.banco.cuenta.infrastructure.ejemplo.in.jms;

import java.util.Optional;

import ec.com.banco.cuenta.infrastructure.common.exceptions.RemoteExecutionException;
import ec.com.banco.cuenta.infrastructure.common.jms.JmsClient;
import ec.com.banco.cuenta.infrastructure.common.jms.JmsPropertiesService;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import ec.com.banco.cuenta.domain.common.exception.ServiceException;
import ec.com.banco.cuenta.domain.ejemplo.integracion.IntegracionProducto;
import ec.com.banco.cuenta.share.ejemplo.enums.Operacion;
import ec.com.banco.cuenta.share.ejemplo.dto.ProductoDto;
import jakarta.jms.JMSException;

@Service
public class IntegracionProductoImpl implements IntegracionProducto {

	private JmsClient jmsClient;
    private JmsPropertiesService propertiesService;
    
    public IntegracionProductoImpl(JmsClient jmsClient, JmsPropertiesService propertiesService) {
        this.jmsClient = jmsClient;
        this.propertiesService = propertiesService;
    }
	
	@Override
	public ProductoDto buscarProducto(Long idProducto, Optional<String> apiVersion) {
		ProductoDto dto = ProductoDto.builder().id(idProducto).build();
		try {
			return jmsClient.sendAndWaitForResponse(dto, ProductoDto.class, propertiesService.getRequestqueue(), 
					propertiesService.getReplyqueue(), apiVersion, Operacion.GET_PRODUCT.name());
		} catch (JsonProcessingException | RemoteExecutionException | JMSException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
}
