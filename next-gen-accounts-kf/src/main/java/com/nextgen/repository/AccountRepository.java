package com.nextgen.repository;

import com.nextgen.entity.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<CustomerAccount, Long> {
    CustomerAccount findByCustomerId(String customerId);
}
