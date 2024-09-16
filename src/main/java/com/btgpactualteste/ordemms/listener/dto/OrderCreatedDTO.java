package com.btgpactualteste.ordemms.listener.dto;

import java.util.List;

public record OrderCreatedDTO(
        Long codigoPedido,
        Long codigoCliente,
        List<OrderItemDTO> itens
) {
}
