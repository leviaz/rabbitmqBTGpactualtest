package com.btgpactualteste.ordemms.listener.dto;

import java.math.BigDecimal;

public record OrderItemDTO(
        String produto,
        Integer quantidade,
        BigDecimal preco

) {
}
