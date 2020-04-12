package com.gra.local.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "vendor_account", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "phone",
                "password"
        })
})
@Getter
@Setter
public class VendorAccount extends DefaultDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "name")
    private String name;

    @Column(columnDefinition = "phone")
    private String phone;

    @Email
    @Column(columnDefinition = "email")
    private String email;

    @Column(columnDefinition = "city")
    private String city;

    @Column(columnDefinition = "verified")
    @Builder.Default()
    private boolean verified = false;

    @Column(columnDefinition = "verifying_code")
    private String verifyingCode;

    @JsonIgnore
    @Column(columnDefinition = "password")
    private String password;
}
