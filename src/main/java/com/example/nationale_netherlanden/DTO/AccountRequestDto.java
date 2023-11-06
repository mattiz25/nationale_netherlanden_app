package com.example.nationale_netherlanden.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDto {
    private String firstName;
    private String lastName;
    private double initialBalancePLN;
}
