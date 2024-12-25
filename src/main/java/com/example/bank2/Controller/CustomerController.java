package com.example.bank2.Controller;

import com.example.bank2.API.ApiResponse;
import com.example.bank2.DTO.CustomerDTO;
import com.example.bank2.DTO.EmployeeDTO;
import com.example.bank2.Model.User;
import com.example.bank2.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;



    //Admin -Employee
    @GetMapping("/get-all-customer")
    public ResponseEntity getAllCustomer (){
        return ResponseEntity.status(200).body(customerService.getAllCustomer());
    }

    //Admin -Employee
    @GetMapping("/get-customer-by-id/{id}")
    public ResponseEntity getCustomerById (@PathVariable Integer id){
        return ResponseEntity.status(200).body(customerService.getCustomerById(id));
    }


    @PostMapping("/register")
    public ResponseEntity register (@RequestBody @Valid CustomerDTO customerDTO){
        customerService.register(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Customer added successfully"));
    }


    @PutMapping("/update")
    public ResponseEntity update (@AuthenticationPrincipal User user, @RequestBody @Valid CustomerDTO  customerDTO){
        customerService.update(user.getId(),customerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
    }

    //customer
    @DeleteMapping("/delete")
    public ResponseEntity delete (@AuthenticationPrincipal User user ){
        customerService.delete(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
    }




}
