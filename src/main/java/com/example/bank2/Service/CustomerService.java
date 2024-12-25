package com.example.bank2.Service;

import com.example.bank2.API.ApiException;
import com.example.bank2.DTO.CustomerDTO;
import com.example.bank2.DTO.EmployeeDTO;
import com.example.bank2.Model.Customer;
import com.example.bank2.Model.Employee;
import com.example.bank2.Model.User;
import com.example.bank2.Repository.AuthRepository;
import com.example.bank2.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;


    //Admin -Employee
    public List<Customer> getAllCustomer (){
        return customerRepository.findAll();
    }
    //Admin - Employee
    public Customer getCustomerById (Integer id){
        return customerRepository.findCustomerById(id);
    }


    public void register (CustomerDTO customerDTO){
        User user = new User();
        user.setUsername(customerDTO.getUsername());
        String hashPassword= new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        user.setPassword(hashPassword);
        user.setName(customerDTO.getName());
        user.setEmail(customerDTO.getEmail());
        user.setRole("CUSTOMER");
        authRepository.save(user);
        Customer customer= new Customer();
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setUser(user);
        customerRepository.save(customer);

    }


    //customer
    public void update (Integer user_id , CustomerDTO customerDTO){

        User user = authRepository.findUserById(user_id);
        if (user==null){
            throw new ApiException("wrong user id");
        }

        Customer customer= customerRepository.findCustomerByUser(user);

        user.setUsername(customerDTO.getUsername());
        user.setUsername(customerDTO.getUsername());
        String hashPassword= new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        user.setPassword(hashPassword);
        user.setName(customerDTO.getName());
        user.setEmail(customerDTO.getEmail());
        authRepository.save(user);
        customer.setPhoneNumber(customer.getPhoneNumber());
        customerRepository.save(customer);

    }

    //customer
    public void delete (Integer user_id){
        User user = authRepository.findUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");
        }

        Customer customer= user.getCustomer();
        customerRepository.delete(customer);
        user.setCustomer(null);
        authRepository.delete(user);

    }



}
