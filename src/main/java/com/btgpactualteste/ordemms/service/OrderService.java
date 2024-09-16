package com.btgpactualteste.ordemms.service;

import com.btgpactualteste.ordemms.entity.OrderEntity;
import com.btgpactualteste.ordemms.entity.OrderItem;
import com.btgpactualteste.ordemms.listener.dto.OrderCreatedDTO;
import com.btgpactualteste.ordemms.repository.OrderRepository;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    public OrderRepository orderRepository;

    public void save (OrderCreatedDTO orderCreatedDTO){
        ModelMapper mapper = new ModelMapper();
        OrderEntity orderEntity = mapper.map(orderCreatedDTO,OrderEntity.class);
      
        // Acess all the itens and create new itens and add them to the itens lits of the orderEntity
        orderEntity.setItens(getList(orderCreatedDTO));
        //Calculate total by
        orderEntity.setTotal(calculateTotal(orderCreatedDTO));
        orderRepository.save(orderEntity);

    }

    private static List<OrderItem> getList(OrderCreatedDTO orderCreatedDTO) {
        return orderCreatedDTO
                .itens()
                .stream()
                .map(i -> new OrderItem(i.produto(), i.quantidade(), i.preco())).toList();
    }

    private BigDecimal calculateTotal(OrderCreatedDTO orderCreatedDTO) {
        return orderCreatedDTO.itens()
                .stream()
                .map(i -> i.preco().multiply(BigDecimal.valueOf(i.quantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
