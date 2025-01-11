package ec.com.banco.cuenta.infrastructure.ejemplo.in.jms;

import ec.com.banco.cuenta.infrastructure.common.jms.JmsClient;
import ec.com.banco.cuenta.infrastructure.common.jms.JmsPropertiesService;
import jakarta.jms.TextMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;

@Service
@Log4j2
public class ProductoMessageListener {
    
	private JmsClient jmsClient;

    private JmsPropertiesService propertiesService;

    public ProductoMessageListener(JmsClient jmsClient, JmsPropertiesService propertiesService) {
        this.jmsClient = jmsClient;
		this.propertiesService = propertiesService;
    }

    @JmsListener(destination = "${jms.products.requestqueue}")
    public void receiveMessage(final Message message, Session session) throws JMSException {
    	log.info("Mensaje recebido y procesando");
		TextMessage textMessage = (TextMessage) message;
//		ObjectMapper objectMapper = new ObjectMapper();
		try {
//			ProductoDto productDto = objectMapper.readValue(textMessage.getText(), ProductoDto.class);
	        jmsClient.executeAndResponse(message, session, propertiesService.getReplyqueue());
		} catch (JsonProcessingException | JMSException e) {
			e.printStackTrace();
		}
    }

}
