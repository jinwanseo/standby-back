package com.standbytogetherbackend.customer.repository;

import com.standbytogetherbackend.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Boolean existsByPhone(String phone);
}
