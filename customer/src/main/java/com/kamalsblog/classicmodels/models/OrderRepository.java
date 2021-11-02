package com.kamalsblog.classicmodels.models;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface OrderRepository extends MongoRepository<Order, Integer> {  
}