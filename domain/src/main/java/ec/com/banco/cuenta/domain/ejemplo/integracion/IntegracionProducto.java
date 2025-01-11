package ec.com.banco.cuenta.domain.ejemplo.integracion;

import java.util.Optional;

import ec.com.banco.cuenta.share.ejemplo.dto.ProductoDto;

public interface IntegracionProducto {

	ProductoDto buscarProducto(Long idProducto, Optional<String> version);

}
