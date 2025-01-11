package ec.com.banco.cuenta.infrastructure.ejemplo;

import ec.com.banco.cuenta.infrastructure.common.jms.JmsPropertiesService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(JmsPropertiesService.class)
public class MyService {

	private final JmsPropertiesService serviceProperties;

	public MyService(JmsPropertiesService serviceProperties) {
		this.serviceProperties = serviceProperties;
	}

	public String message() {
		return this.serviceProperties.getMessage();
	}
}
