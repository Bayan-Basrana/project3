package com.example.bank2.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    private Integer id;

    @NotEmpty(message = "position is required")
    @Column(columnDefinition = "varchar(20) not null")
    private String position;

    @NotNull(message = "salary is required")
    @DecimalMin(value = "0.0",inclusive = true ,message = "Salary Must be a non-negative decimal number.")
    @Column(columnDefinition = "double not null")
    private Double salary;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;



}
