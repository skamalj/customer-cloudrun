package com.kamalsblog.classicmodels;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface CustomerRepository extends MongoRepository<Customer, Integer> {  
}