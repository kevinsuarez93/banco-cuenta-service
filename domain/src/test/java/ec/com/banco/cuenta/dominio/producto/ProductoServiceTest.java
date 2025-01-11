package ec.com.banco.cuenta.dominio.producto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ec.com.banco.cuenta.domain.ejemplo.producto.Producto;
import ec.com.banco.cuenta.domain.ejemplo.producto.ProductoRepository;
import ec.com.banco.cuenta.domain.ejemplo.producto.ProductoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {
	
	@Mock
	public ProductoRepository productRepository;
	
	@InjectMocks
	public ProductoServiceImpl productService;
	
	@Test
	public void shouldSave() {
		productService.updateProduct(new Producto());
		
		verify(productRepository, times(1)).updateProduct(any(Producto.class));
	}

	
}
