package com.example.bank2.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomerDTO {

    @Size(min = 4,max = 10 ,message = "username must be between 4 and 10 characters")
    private String username;

    @Size(min = 6)
    private String password;

    @Size(min = 2,max = 20 ,message = "name must be between 2 and 20 characters")
    private String name;

    @Email(message = "invalid email")
    private String email;

    @Pattern(regexp = "05\\d{8}",message = "Must start with \"05\"")
    private String  phoneNumber;


}
