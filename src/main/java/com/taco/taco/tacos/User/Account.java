package com.taco.taco.tacos.User;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter @Setter @EqualsAndHashCode(of="id")
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
public class Account {
    @Id @GeneratedValue
    private Long id;

    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;

}
