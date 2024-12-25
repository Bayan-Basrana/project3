package com.example.bank2.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EmployeeDTO {


    @Size(min = 4,max = 10 ,message = "username must be between 4 and 10 characters")
    private String username;

    @Size(min = 6, message = "password must be at least 6")
    private String password;

    @Size(min = 2,max = 20 ,message = "name must be between 2 and 20 characters")
    private String name;

    @Email(message = "invalid email")
    private String email;

    @NotEmpty(message = "position is required")
    private String position;

    @DecimalMin(value = "0.0",inclusive = true ,message = "Salary Must be a non-negative decimal number.")
    @NotNull(message = "salary is required")
    private Double salary;


}
