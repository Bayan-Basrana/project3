package com.example.bank2.Service;

import com.example.bank2.API.ApiException;
import com.example.bank2.DTO.EmployeeDTO;
import com.example.bank2.Model.Customer;
import com.example.bank2.Model.Employee;
import com.example.bank2.Model.User;
import com.example.bank2.Repository.AuthRepository;
import com.example.bank2.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;



    //Admin
    public List<Employee> getAllEmployee (){
        return employeeRepository.findAll();
    }
    //Admin
    public Employee getEmployeeById (Integer id){
        return employeeRepository.findEmployeeById(id);
    }


    public void register (EmployeeDTO employeeDTO){
        User user = new User();
       user.setUsername(employeeDTO.getUsername());
        String hashPassword= new BCryptPasswordEncoder().encode(employeeDTO.getPassword());
        user.setPassword(hashPassword);
       user.setName(employeeDTO.getName());
       user.setEmail(employeeDTO.getEmail());
       user.setRole("EMPLOYEE");
        authRepository.save(user);
        Employee employee= new Employee();
        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());
        employee.setUser(user);
        employeeRepository.save(employee);

    }


    public void update (Integer user_id , EmployeeDTO employeeDTO){

        User user = authRepository.findUserById(user_id);
        if (user==null){
            throw new ApiException("wrong user id");
        }

        Employee employee= employeeRepository.findEmployeeByUser(user);

        user.setUsername(employeeDTO.getUsername());
        user.setUsername(employeeDTO.getUsername());
        String hashPassword= new BCryptPasswordEncoder().encode(employeeDTO.getPassword());
        user.setPassword(hashPassword);
        user.setName(employeeDTO.getName());
        user.setEmail(employeeDTO.getEmail());
        authRepository.save(user);
        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());
        employeeRepository.save(employee);

    }

    public void delete (Integer user_id){
        User user = authRepository.findUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");
        }

        Employee employee= user.getEmployee();
        employeeRepository.delete(employee);
        user.setEmployee(null);
        authRepository.delete(user);

    }

}
