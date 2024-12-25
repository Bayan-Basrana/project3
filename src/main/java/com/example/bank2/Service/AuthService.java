package com.example.bank2.Service;

import com.example.bank2.API.ApiException;
import com.example.bank2.Model.Customer;
import com.example.bank2.Model.User;
import com.example.bank2.Repository.AuthRepository;
import com.example.bank2.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;



    //admin
    public List<User> getAllUser (){
        return authRepository.findAll();
    }





}
