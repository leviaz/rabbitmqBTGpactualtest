package com.btgpactualteste.ordemms.repository;

import com.btgpactualteste.ordemms.entity.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<OrderEntity,Long> {


}
