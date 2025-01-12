package ec.com.banco.cuenta.infrastructure.ejemplo;

import ec.com.banco.cuenta.infrastructure.common.jms.JmsCuentaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(JmsCuentaProperties.class)
public class MyService {

	private final JmsCuentaProperties serviceProperties;

	public MyService(JmsCuentaProperties serviceProperties) {
		this.serviceProperties = serviceProperties;
	}

	public String message() {
		return this.serviceProperties.getMessage();
	}
}
