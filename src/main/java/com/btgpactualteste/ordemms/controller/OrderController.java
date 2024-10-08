package com.btgpactualteste.ordemms.controller;

import com.btgpactualteste.ordemms.listener.dto.ApiResponse;
import com.btgpactualteste.ordemms.listener.dto.PaginationResponse;
import com.btgpactualteste.ordemms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    //Pagination configs
    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(@PathVariable("customerId") Long customerId,
                                                                 @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){


        var pageResponse = orderService.findAllByCustomerId(customerId, PageRequest.of(page, pageSize));
        //Sumarize the response
        var totalOnOrders = orderService.findTotalByClient(customerId);

        return ResponseEntity.ok(new ApiResponse<>(
                Map.of("totalOrders",totalOnOrders),
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)
        ));
    }
}
