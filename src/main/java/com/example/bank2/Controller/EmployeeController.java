package com.example.bank2.Controller;

import com.example.bank2.API.ApiResponse;
import com.example.bank2.DTO.EmployeeDTO;
import com.example.bank2.Model.User;
import com.example.bank2.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;


    //Admin
    @GetMapping("/get-all-employee")
    public ResponseEntity getAllEmployee (){
        return ResponseEntity.status(200).body(employeeService.getAllEmployee());
    }

    //Admin
    @GetMapping("/get-employee-by-id/{id}")
    public ResponseEntity getEmployeeById (@PathVariable Integer id){
        return ResponseEntity.status(200).body(employeeService.getEmployeeById(id));
    }


    @PostMapping("/register")
    public ResponseEntity register (@RequestBody @Valid EmployeeDTO employeeDTO){
        employeeService.register(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Employee added successfully"));
    }


    //employee
    @PutMapping("/update")
    public ResponseEntity update (@AuthenticationPrincipal User user, @RequestBody @Valid EmployeeDTO employeeDTO){
        employeeService.update(user.getId(),employeeDTO);
        return ResponseEntity.status(200).body(new ApiResponse("update successfully"));
    }

    //employee
    @DeleteMapping("/delete")
    public ResponseEntity delete (@AuthenticationPrincipal User user){
        employeeService.delete(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
    }

}
