package com.gpmall.commons.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class RabbitMqConfig {

    public static final String DELAY_EXCHANGE = "delay_exchange";

    public static final String DELAY_QUEUE = "delay_queue";


    @Bean
    public Queue delayQueue(){
        return new Queue(DELAY_QUEUE, true);
    }


    @Bean
    public FanoutExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        FanoutExchange topic = new FanoutExchange(DELAY_EXCHANGE, true, false, args);
        topic.setDelayed(true);
        return topic;
    }


    @Bean
    public Binding delayBind(){
        return BindingBuilder.bind(delayQueue())
                .to(delayExchange());
    }

    Jackson2JsonMessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }


}
