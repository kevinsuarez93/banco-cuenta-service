package ec.com.banco.cuenta.infrastructure.ejemplo.in.rest;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import ec.com.banco.cuenta.domain.common.exception.EntidadNoEncontradaException;
import ec.com.banco.cuenta.domain.ejemplo.producto.ProductoService;
import ec.com.banco.cuenta.infrastructure.common.exceptions.RemoteExecutionException;
import ec.com.banco.cuenta.infrastructure.ejemplo.mappers.ProductoMapper;
import ec.com.banco.cuenta.share.ejemplo.dto.ProductoDto;
import jakarta.jms.JMSException;

@RestController
@RequestMapping("/productsipc")
public class ProductoIpcRestAdapter {
	
    private ProductoService productoService;
    
    private ProductoMapper productoMapper;

    public ProductoIpcRestAdapter(ProductoService productoService, ProductoMapper productoMapper) {
        this.productoService = productoService;
        this.productoMapper = productoMapper;
    }


    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateProduct(@RequestBody ProductoDto productDto) throws JsonProcessingException, RemoteExecutionException, JMSException {
//        jmsClient.sendAndWaitForResponse(productDto, ProductoDto.class, propertiesService.getRequestqueue(), 
//        		propertiesService.getReplyqueue(), Optional.empty(), Operacion.UPDATE_PRODUCT.name());
    }

}
