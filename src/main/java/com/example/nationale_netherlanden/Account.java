package com.example.nationale_netherlanden;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    public Account(String firstName, String lastName, double balancePLN) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.balancePLN = balancePLN;
        this.accountUid = UUID.randomUUID().toString();
    }

    //    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
////    @SequenceGenerator(name = "account_id", sequenceName = "account_id", allocationSize = 50)
    private String accountUid;
    private String firstName;
    private String lastName;
    double balancePLN;


}
