package ec.com.banco.cuenta.infrastructure.common.jms;

import ec.com.banco.cuenta.infrastructure.cuenta.in.jms.operacion.BuscarCuentaOperation;
import ec.com.banco.cuenta.infrastructure.ejemplo.in.jms.impl.ActualizarProductoOperation;
import ec.com.banco.cuenta.infrastructure.ejemplo.in.jms.impl.ObtenerProductoOperation;
import org.springframework.stereotype.Service;

import ec.com.banco.cuenta.share.ejemplo.enums.Operacion;

@Service
public class ProductoServiceFactory {

	private ObtenerProductoOperation getProduct;
	
	private ActualizarProductoOperation updateProduct;

	private BuscarCuentaOperation buscarCuenta;
	
	public ProductoServiceFactory(ObtenerProductoOperation getProduct, ActualizarProductoOperation updateProduct, BuscarCuentaOperation buscarCuenta) {
		this.getProduct = getProduct;
		this.updateProduct = updateProduct;
		this.buscarCuenta = buscarCuenta;
	}
	
	public Servicio getInstance(String operacion) {
		Operacion o = null;
		try {
			o = Operacion.valueOf(operacion);
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format("Operacion no soportada %s", operacion));
		}
		switch (o) {
		case UPDATE_PRODUCT:
			return updateProduct;
		case GET_PRODUCT:
			return getProduct;
		case GET_CUENTA_POR_ID:
			return buscarCuenta;
		default:
			throw new IllegalArgumentException(String.format("Operacion no soportada %s", operacion));
		}
		
	}
}
