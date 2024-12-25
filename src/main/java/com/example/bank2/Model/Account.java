package com.example.bank2.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "accountNumber is required")
    @Pattern(regexp = "\\d{4}-\\d{4}-\\d{4}-\\d{4}", message = "Account number must follow the format XXXX-XXXX-XXXX-XXXX")
    @Column(columnDefinition = "varchar(100) not null unique")
    private String accountNumber;

    @NotNull(message = "balance is required")
    @Positive(message = "balance is required ")
    @Column(columnDefinition = "double not null")
    private Double balance;

    @Column(columnDefinition = "boolean not null")
    private Boolean isActive = false;

    @ManyToOne
    @JsonIgnore
    private Customer customer;


}
