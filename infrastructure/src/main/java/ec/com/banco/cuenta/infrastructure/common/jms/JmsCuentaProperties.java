package ec.com.banco.cuenta.infrastructure.common.jms;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("jms.products")
@Getter
@Setter
public class JmsCuentaProperties {

	private String message;
	private String replyqueue;
	private String requestqueue;

}
