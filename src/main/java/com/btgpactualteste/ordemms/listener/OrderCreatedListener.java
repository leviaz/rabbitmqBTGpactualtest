package com.btgpactualteste.ordemms.listener;

import com.btgpactualteste.ordemms.listener.dto.OrderCreatedDTO;
import com.btgpactualteste.ordemms.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.btgpactualteste.ordemms.config.RabbitmqConfig.ORDER_CREATED_QUEUE;

@Component
public class OrderCreatedListener {

    private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    @Autowired
    private OrderService orderService;
    //Grant acess to payload and convert to json
    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listen (Message<OrderCreatedDTO> message){
        logger.info("Message consumed: {}",message);
        //pass the json body to the save repository service
        orderService.save(message.getPayload());
    }
}
