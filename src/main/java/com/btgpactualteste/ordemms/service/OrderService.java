package com.btgpactualteste.ordemms.service;

import com.btgpactualteste.ordemms.controller.OrderResponse;
import com.btgpactualteste.ordemms.entity.OrderEntity;
import com.btgpactualteste.ordemms.entity.OrderItem;
import com.btgpactualteste.ordemms.listener.dto.OrderCreatedDTO;
import com.btgpactualteste.ordemms.repository.OrderRepository;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    public OrderRepository orderRepository;

    public void save (OrderCreatedDTO orderCreatedDTO){
        var entity = new OrderEntity();

        entity.setOrderId(orderCreatedDTO.codigoPedido());
        entity.setCustomerId(orderCreatedDTO.codigoCliente());
        entity.setItems(getOrderItems(orderCreatedDTO));
        entity.setTotal(getTotal(orderCreatedDTO));

        orderRepository.save(entity);



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


    private List<OrderEntity> findAllByCustomer(Long id){
        List<OrderEntity> orderlist = orderRepository.findAll();
        return orderlist.stream().filter(order -> order.getCustomerId()==id)
                .collect(Collectors.toList());
    }

    public Page<OrderResponse> findAllByCustomerId(Long customerId,PageRequest pagerequest){
        var orders = orderRepository.findAllByCustomerId(customerId,pagerequest);
        System.out.println(orders.toString());
        //Convert all orders to response
        return orders.map(OrderResponse::fromEntity);
    }

    private BigDecimal getTotal(OrderCreatedDTO event) {
        return event.itens()
                .stream()
                .map(i -> i.preco().multiply(BigDecimal.valueOf(i.quantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private static List<OrderItem> getOrderItems(OrderCreatedDTO event) {
        return event.itens().stream()
                .map(i -> new OrderItem(i.produto(), i.quantidade(), i.preco()))
                .toList();
    }



}
