package ec.com.banco.cuenta.infrastructure.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("jms.cuentas")
@Getter
@Setter
public class JmsCuentaProperties {

	private String message;
	private String replyqueue;
	private String requestqueue;

}
