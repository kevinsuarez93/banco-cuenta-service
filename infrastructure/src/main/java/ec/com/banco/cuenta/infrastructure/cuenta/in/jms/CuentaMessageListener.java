package ec.com.banco.cuenta.infrastructure.cuenta.in.jms;

import ec.com.banco.cuenta.infrastructure.common.jms.JmsClient;
import ec.com.banco.cuenta.infrastructure.common.JmsCuentaProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;

@Service
@Log4j2
@EnableConfigurationProperties(JmsCuentaProperties.class)
public class CuentaMessageListener {
    
	private JmsClient jmsClient;

    private JmsCuentaProperties jmsCuentaProperties;

    public CuentaMessageListener(JmsClient jmsClient, JmsCuentaProperties jmsCuentaProperties) {
        this.jmsClient = jmsClient;
		this.jmsCuentaProperties = jmsCuentaProperties;
    }

    @JmsListener(destination = "${jms.cuentas.requestqueue}")
    public void receiveMessage(final Message message, Session session) throws JMSException {
		log.info("Received message: " + message);
		try {
			jmsClient.executeAndResponse(message, session, jmsCuentaProperties.getReplyqueue());
		} catch (JsonProcessingException | JMSException e) {
			log.error(e.getMessage());
		}
    }

}
