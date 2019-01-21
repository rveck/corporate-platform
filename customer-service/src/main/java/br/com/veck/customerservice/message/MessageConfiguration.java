package br.com.veck.customerservice.message;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MessageConfiguration {
	
	@Value("${rabbitmq.customer.exchange}")
	private String rabbitMqExchange;
	
	@Value("${rabbitmq.customer.routingkey}")
	private String rabbitMqRoutingKey;
	
	@Value("${rabbitmq.customer.queue}")
	private String customersQueue;

    @Bean
    Queue queue() {
        return new Queue(customersQueue, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(rabbitMqExchange);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(rabbitMqRoutingKey);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(customersQueue);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(CustomerMessageProcessor messageProcessor) {
        return new MessageListenerAdapter(messageProcessor, "processMessage");
    }

}
