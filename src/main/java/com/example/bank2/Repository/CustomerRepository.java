package com.example.bank2.Repository;

import com.example.bank2.Model.Customer;
import com.example.bank2.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findCustomerById (Integer id);

    Customer findCustomerByUser (User user);
}
