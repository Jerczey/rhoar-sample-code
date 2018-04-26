package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.apache.camel.component.amqp.AMQPComponent;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableScheduling
public class Application extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean(name = "amqp-component")
	AMQPComponent amqpComponent(AMQPConfiguration config) {
			JmsConnectionFactory qpid = new JmsConnectionFactory(config.getUsername(), config.getPassword(), "amqp://"+ config.getHost() + ":" + config.getPort());
			qpid.setTopicPrefix("topic://");

			PooledConnectionFactory factory = new PooledConnectionFactory();
			factory.setConnectionFactory(qpid);

			return new AMQPComponent(factory);
	}

}
