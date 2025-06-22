package com.ouronet.ouronet.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

import lombok.Data;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    private String email;
    private String hashedPassword;
    private String vipState;

    public void setPassword(String password) {
        this.hashedPassword = password;
    }


}
