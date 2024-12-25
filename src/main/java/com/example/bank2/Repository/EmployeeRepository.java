package com.example.bank2.Repository;

import com.example.bank2.Model.Employee;
import com.example.bank2.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee , Integer> {
    Employee findEmployeeById (Integer id);

    Employee findEmployeeByUser (User user);
}
