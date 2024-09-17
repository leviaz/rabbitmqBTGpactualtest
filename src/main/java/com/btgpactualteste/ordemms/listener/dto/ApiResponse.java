package com.btgpactualteste.ordemms.listener.dto;

import java.util.List;

public record ApiResponse<T>(List<T> data,PaginationResponse paginationResponse) {
}
