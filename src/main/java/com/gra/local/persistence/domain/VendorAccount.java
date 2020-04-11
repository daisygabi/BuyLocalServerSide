package com.gra.local.persistence.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "vendor_account")
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

    @Column(columnDefinition = "email")
    private String email;

    @Column(columnDefinition = "city")
    private String city;

    @Column(columnDefinition = "verified")
    private boolean verified;

    @Column(columnDefinition = "verifying_code")
    private String verifyingCode;
}

